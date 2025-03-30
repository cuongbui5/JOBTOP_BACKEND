package com.example.jobs_top.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.*;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.*;

@Service
public class CustomerSupportAssistant {
    private final ChatClient chatClient;
    /*
     Bạn là nhân viên hỗ trợ trò chuyện khách hàng của một hãng hàng không có tên "Job Top".
                        Trả lời theo cách thân thiện, hữu ích và vui vẻ.
                        Bạn đang tương tác với khách hàng thông qua hệ thống trò chuyện trực tuyến.
                        Trước khi cung cấp thông tin về đơn ứng tuyển, BẠN PHẢI luôn lấy thông tin sau từ người dùng: họ tên đầy đủ, tên của công việc và tên công ty mà người dùng ứng tuyển.
                        Kiểm tra lịch sử tin nhắn để biết thông tin này trước khi hỏi người dùng.
						Use parallel function calling if required.
						Hôm nay là {current_date}.

						You are a customer service representative for a recruitment company called "Job Top".
                        Respond in a friendly, helpful, and joyful manner.
                        You are interacting with customers through an online chat system.
                        Before providing information about a application, you MUST always get the following information from the user: full name, job title and company name.
                        Check the message history for this information before asking the user.
                        Use the provided functions to fetch application details.
						Use parallel function calling if required.
						Today is {current_date}.
    * */


    public CustomerSupportAssistant(ChatClient.Builder modelBuilder, VectorStore vectorStore, ChatMemory chatMemory) {
        this.chatClient = modelBuilder
                .defaultSystem("""
                        You are a CUSTOMER SUPPORT AGENT for JobTop, an online recruitment platform.
                        Respond in a friendly, helpful, and joyful manner.
                        You are interacting with customers through an online chat system.
                        Before providing information about an application, withdrawing a job application, or assisting users in applying for a desired position, always ensure you collect the following user details: username, job title, and company name.
                        - Check the message history for these details before asking the user directly to avoid redundancy. 
                        - Politely request missing details, if any.
                        Use the provided system functions to: 
                        - Fetch application details. 
                        - Withdraw a job applications upon user request, ensuring intent is confirmed beforehand. 
                        - Assist users in applying for a desired position.
                        - Handle errors gracefully and provide alternative solutions if withdrawal is unsuccessful. 
                        If required, make use of parallel function calls to ensure efficiency.    
                        Today is {current_date}.
			    """)
                .defaultAdvisors(
                        new PromptChatMemoryAdvisor(chatMemory),
                        // new PromptChatMemoryAdvisor(chatMemory)                  //  Chat Memory
                        // new VectorStoreChatMemoryAdvisor(vectorStore)),

                        //new QuestionAnswerAdvisor(vectorStore, SearchRequest.defaults()), // RAG
                        // 	.withFilterExpression("'documentType' == 'terms-of-service' && region in ['EU', 'US']")),
                        new SimpleLoggerAdvisor())
                .defaultFunctions("getApplicationDetails","withdrawJobApplication","applyJobApplication") // FUNCTION CALLING
                .build();

    }

    public String chat(String chatId, String userMessageContent) {
        return this.chatClient.prompt()
                .system(s -> s.param("current_date", LocalDate.now().toString()))
                .user(userMessageContent)
                .advisors(a -> a
                        .param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId)
                        .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 100)
                       // .param(QuestionAnswerAdvisor.FILTER_EXPRESSION, "chatId == '" + chatId + "'") // Sửa phần bộ lọc
                )
                .call().content();
    }

    public Flux<String> chatStreaming(String chatId, String userMessageContent) {
        return this.chatClient.prompt()
                .system(s -> s.param("current_date", LocalDate.now().toString()))
                .user(userMessageContent)
                .advisors(a -> a
                                .param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId)
                                .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 100)
                        // .param(QuestionAnswerAdvisor.FILTER_EXPRESSION, "chatId == '" + chatId + "'") // Sửa phần bộ lọc
                )
                .stream().content();
    }



}
