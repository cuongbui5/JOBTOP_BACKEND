package com.example.jobs_top;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;
//import org.springframework.ai.anthropic.AnthropicChatModel;
//import org.springframework.ai.anthropic.api.AnthropicApi;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
//import org.springframework.ai.chroma.ChromaApi;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.document.Document;
import org.springframework.ai.document.MetadataMode;
import org.springframework.ai.embedding.EmbeddingModel;

import org.springframework.ai.embedding.EmbeddingRequest;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.ai.embedding.TokenCountBatchingStrategy;
//import org.springframework.ai.mistralai.MistralAiEmbeddingModel;
//import org.springframework.ai.mistralai.api.MistralAiApi;
import org.springframework.ai.reader.TextReader;
//import org.springframework.ai.retry.RetryUtils;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
//import org.springframework.ai.vectorstore.ChromaVectorStore;
import org.springframework.ai.vectorstore.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@Configuration
@EnableScheduling
public class JobsTopApplication {

    @Bean
    public ChatMemory chatMemory(){
        return new InMemoryChatMemory();

    }


    /*@Bean
    public RestClient.Builder builder() {
        return RestClient.builder().requestFactory(new SimpleClientHttpRequestFactory());
    }
     @Bean
    public VectorStore vectorStore(EmbeddingModel embeddingModel){
        return SimpleVectorStore.builder(embeddingModel).build();
    }



    @Bean
    public ChromaApi chromaApi(RestClient.Builder restClientBuilder) {
        String chromaUrl = "http://localhost:8000";
        return new ChromaApi(chromaUrl, restClientBuilder);
    }




    @Bean
    public VectorStore chromaVectorStore(EmbeddingModel embeddingModel, ChromaApi chromaApi) {
        return new ChromaVectorStore(embeddingModel, chromaApi,"SpringAiCollection",true);
    }

    @Bean
    public ChatModel chatModel() {
        return new AnthropicChatModel(new AnthropicApi("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"));
    }

   */

    // This can be any EmbeddingModel implementation



    //@Bean
    CommandLineRunner ingestTermOfServiceToVectorStore(
            VectorStore vectorStore,
            @Value("classpath:rag/terms-of-service.txt") Resource termsOfServiceDocs) {

        return args -> {
            vectorStore.write(
                    new TokenTextSplitter().transform(
                            new TextReader(termsOfServiceDocs).read()));

            /*


            vectorStore.similaritySearch("Cancelling Bookings").forEach(doc -> {
                //logger.info("Similar Document: {}", doc.getContent());
                System.out.println(doc.getContent());
            });

            */
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(JobsTopApplication.class, args);
    }

}
