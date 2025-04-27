package com.example.jobs_top.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.Conflicts;
import co.elastic.clients.elasticsearch.core.DeleteByQueryResponse;
import com.example.jobs_top.model.Job;
import com.example.jobs_top.model.enums.ExperienceLevel;
import com.example.jobs_top.model.enums.JobType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.time.Duration;
import java.util.*;


@Service
public class ElasticService {
    private final VectorStore vectorStore;
    private final ElasticsearchClient elasticsearchClient;
    private final TokenTextSplitter tokenTextSplitter;
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    public ElasticService(VectorStore vectorStore, ElasticsearchClient elasticsearchClient, TokenTextSplitter tokenTextSplitter, RedisTemplate<String, Object> redisTemplate, ObjectMapper objectMapper) {
        this.vectorStore = vectorStore;
        this.elasticsearchClient = elasticsearchClient;
        this.tokenTextSplitter = tokenTextSplitter;
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }
    private String safe(Object o) {
        return o == null ? "" : o.toString();
    }

    public void upsertJobDocument(Job job) {
        deleteJobDocument(job.getId());
        saveJobDocument(job);
    }

    public void deleteAllDocuments(String indexName) {
        try {
            DeleteByQueryResponse response = elasticsearchClient.deleteByQuery(b -> b
                    .index(indexName)
                    .query(q -> q.matchAll(m -> m))
                    .conflicts(Conflicts.Proceed)
            );
            System.out.println("✅ Deleted documents: " + response.deleted());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }




    public List<Job> sematicSearchJobDocumentCache(String query) throws JsonProcessingException {
        String key = "job:search:ai" + query.toLowerCase();
        Object cachedObj = redisTemplate.opsForValue().get(key);

        if (cachedObj != null) {
            // Parse the JSON string manually
            return objectMapper.readValue((String)cachedObj, new TypeReference<List<Job>>() {});
        }
        List<Document> results= vectorStore
                .similaritySearch(SearchRequest.query("Tìm công việc liên quan đến: "+query.toLowerCase())
                .withTopK(20).withSimilarityThreshold(0.8));




        if (results.isEmpty()) {
            // Cache empty results too to avoid repeated searches
            redisTemplate.opsForValue().set(key, List.of(), Duration.ofHours(1));
            return List.of();
        }

        // Map documents to jobs
        List<Job> jobs = results.stream()
                .map(doc -> mapToJob(doc.getMetadata()))
                .toList();

        // Store in cache with expiration
        redisTemplate.opsForValue().set(key,objectMapper.writeValueAsString(jobs) , Duration.ofHours(1));

        return jobs;

    }

    public List<Job> sematicSearchJobDocument(String query) {
        List<Document> results= vectorStore
                .similaritySearch(SearchRequest.query("Tìm công việc liên quan đến: "+query.toLowerCase())
                        .withTopK(20).withSimilarityThreshold(0.8));


        if (results.isEmpty()) {
            return List.of();
        }

        return results.stream().map(doc -> mapToJob(doc.getMetadata())).toList();

    }



    public Job mapToJob(Map<String, Object> metadata) {
        Job job = new Job();
        job.setId(Long.parseLong(metadata.get("jobId").toString()));
        job.setTitle(metadata.get("title").toString());
        job.setJobType(JobType.valueOf((String) metadata.get("jobType")));
        job.setCity((String) metadata.get("city"));
        job.setExperienceLevel(ExperienceLevel.valueOf((String) metadata.get("experienceLevel")));
        job.setSalaryMin((Integer) metadata.get("salaryMin"));
        job.setSalaryMax((Integer) metadata.get("salaryMax"));
        job.setRequirements((String) metadata.get("requirements"));
        job.setDescription((String) metadata.get("description"));
        return job;
    }

    public void saveJobDocument(Job job){
        try {
            String content = String.format(
                    "Tên công việc: %s. Mô tả: %s. " +
                            "Thành phố: %s. Yêu cầu kinh nghiệm: %s. " +
                            "Yêu cầu công việc: %s. Quyền lợi: %s.",
                    safe(job.getTitle()),
                    safe(job.getDescription()),
                    safe(job.getCity()),
                    safe(job.getExperienceLevel().getLabel()),
                    safe(job.getRequirements()),
                    safe(job.getBenefits())
            );


            Map<String, Object> metadata = Map.of(
                    "jobId", job.getId(),
                    "title", job.getTitle(),
                    "city", job.getCity(),
                    "jobType", job.getJobType().toString(),
                    "experienceLevel", job.getExperienceLevel().toString(),
                    "salaryMin", job.getSalaryMin(),
                    "salaryMax", job.getSalaryMax(),
                    "requirements", job.getRequirements(),
                    "description", job.getDescription()
            );

            var document = new Document(content,metadata);
            var split = tokenTextSplitter.apply(List.of(document));
            vectorStore.add(split);
            System.out.println("Created Job id: " + job.getId());


        }catch (Exception e){
            e.printStackTrace();

            throw new RuntimeException(e);



        }

    }

    public void deleteJobDocument(Long jobId) {
        try {
            DeleteByQueryResponse response = elasticsearchClient.deleteByQuery(d -> d
                    .index("custom_index3")
                    .conflicts(Conflicts.Proceed)
                    .query(q -> q
                            .term(t -> t
                                    .field("metadata.jobId")
                                    .value(String.valueOf(jobId))
                            )
                    )
            );

            System.out.println("✅ Deleted " + response.deleted() + " documents for jobId: " + jobId);
        } catch (IOException e) {
            System.err.println("❌ Error deleting job: " + e.getMessage());
            e.printStackTrace();
        }

    }
}
