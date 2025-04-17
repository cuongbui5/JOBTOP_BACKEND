package com.example.jobs_top.service;

import com.example.jobs_top.dto.req.EvaluateRequest;
import com.example.jobs_top.model.Account;
import com.example.jobs_top.model.Job;
import com.example.jobs_top.model.Resume;
import com.example.jobs_top.repository.JobRepository;
import com.example.jobs_top.repository.ResumeRepository;
import com.example.jobs_top.utils.Utils;
import org.apache.tika.Tika;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY;
import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_RETRIEVE_SIZE_KEY;


@Service
public class AiService {
    private final ChatClient chatClient;
    private final JobRepository jobRepository;
    private final ResumeRepository resumeRepository;
    private final ElasticService elasticService;

    public AiService(ChatClient.Builder modelBuilder, ChatMemory chatMemory, JobRepository jobRepository, ResumeRepository resumeRepository, ElasticService elasticService) {
        this.elasticService = elasticService;
        this.chatClient = modelBuilder
                .defaultSystem("""
                   Bạn là một chuyên gia tuyển dụng. Dựa vào CV và YÊU CẦU CÔNG VIỆC bên dưới, hãy đánh giá mức độ phù hợp của ứng viên.
                   Đưa ra lý do rõ ràng, các điểm mạnh/yếu, và lời khuyên cải thiện. Tránh nói lan man.
                   Hãy đánh giá mức độ phù hợp của ứng viên với công việc này. Trả lời bằng:
                    - Mức độ phù hợp (Cao / Trung bình / Thấp)
                    - Giải thích ngắn gọn lý do
                    - (Nếu có thể) Gợi ý những điểm cần cải thiện
       """
        ).defaultAdvisors(
                        new PromptChatMemoryAdvisor(chatMemory),
                        // new PromptChatMemoryAdvisor(chatMemory)                  //  Chat Memory
                        // new VectorStoreChatMemoryAdvisor(vectorStore)),

                        //new QuestionAnswerAdvisor(vectorStore, SearchRequest.defaults()), // RAG
                        // 	.withFilterExpression("'documentType' == 'terms-of-service' && region in ['EU', 'US']")),
                        new SimpleLoggerAdvisor())
                .build();
        this.jobRepository = jobRepository;
        this.resumeRepository = resumeRepository;

    }

    public String readCVFromUrl(String url) {
        try (InputStream input = new URL(url).openStream()) {
            Tika tika = new Tika();
            return tika.parseToString(input);
        } catch (Exception e) {
            throw new IllegalArgumentException("Không thể đọc CV từ Cloudinary", e);
        }
    }

    public String evaluateFit(Long jobId) {
        Job job=jobRepository.findById(jobId).orElseThrow(()->new IllegalArgumentException("Not found job"));
        Account account=Utils.getAccount();
        Resume resume=resumeRepository.findById(account.getResumeDefault()).orElseThrow(()->new IllegalArgumentException("Not found resume"));
        String cvText=readCVFromUrl(resume.getLink());
        String jdText=job.getRequirements();
        StringBuilder userMsg = new StringBuilder();
        userMsg.append("Nội dung CV của ứng cử viên: ").append(cvText).append("\n");
        userMsg.append("Yêu cầu công việc ").append(job.getTitle()).append(": ").append(jdText);
        System.out.println(userMsg);

        return chatClient.prompt()
                .user(userMsg.toString())
                .advisors(a -> a
                                .param(CHAT_MEMORY_CONVERSATION_ID_KEY, account.getId())
                                .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 100)
                        // .param(QuestionAnswerAdvisor.FILTER_EXPRESSION, "chatId == '" + chatId + "'") // Sửa phần bộ lọc
                )
                .call().content();
    }

    public List<Job> findJobByCv() {
        Account account=Utils.getAccount();
        Resume resume=resumeRepository.findById(account.getResumeDefault())
                .orElseThrow(()->new IllegalArgumentException("Not found resume"));
        String cvText=readCVFromUrl(resume.getLink());
        return elasticService.sematicSearchJobDocument(cvText);

    }
}
