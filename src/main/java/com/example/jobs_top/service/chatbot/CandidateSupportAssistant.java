package com.example.jobs_top.service.chatbot;

import com.example.jobs_top.model.Account;
import com.example.jobs_top.utils.Utils;
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
public class CandidateSupportAssistant {
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




    public CandidateSupportAssistant(ChatClient.Builder modelBuilder, VectorStore vectorStore, ChatMemory chatMemory) {
        this.chatClient = modelBuilder
                .defaultSystem("""
                         Bạn là một TRỢ LÝ ẢO hỗ trợ người dùng trên nền tảng tuyển dụng JobTop.
                         Nhiệm vụ chính của bạn là:
                         1. Giúp người dùng **tra cứu lịch phỏng vấn** sắp tới.
                         2. Tổ chức các buổi **phỏng vấn giả lập (mock interview)** để họ luyện tập.
                         3. Hỗ trợ **ứng tuyển công việc** theo yêu cầu người dùng.
                         Hãy phản hồi một cách thân thiện, rõ ràng và hữu ích.
                         Khi người dùng muốn tra cứu lịch phỏng vấn:
                         - Bạn chỉ cần sử dụng function calling để gọi hàm thôi.
                         - Hiển thị từng buổi phỏng vấn trên dòng riêng, trình bày rõ ràng, 
                         - Hiển thị thời gian trong **1 dòng duy nhất**, ví dụ: `Thời gian: 16:07 - 17:07`..          
                          Khi người dùng muốn luyện phỏng vấn:
                         - Bắt đầu buổi phỏng vấn dựa trên **vị trí công việc** mà người dùng quan tâm.
                         - Đưa ra câu hỏi phù hợp với vị trí đó, chờ người dùng trả lời.
                         - Sau mỗi câu trả lời, bắt buộc phải đánh giá phản hồi của họ và đưa ra đáp án đúng nhất cho họ tham khảo.
                         Khi người dùng muốn **ứng tuyển công việc**:
                         - Hỏi người dùng cung cấp các thông tin: **tên công việc, tên công ty**.
                         - Sau đó sử dụng function calling để gọi hàm.
                         - Thông báo lại kết quả ứng tuyển cho người dùng một cách thân thiện.
                         Luôn kiểm tra lịch sử hội thoại trước khi hỏi lại người dùng để tránh gây phiền.
                          Hôm nay là {current_date}.
			    """)
                .defaultAdvisors(
                        new PromptChatMemoryAdvisor(chatMemory),
                        // new PromptChatMemoryAdvisor(chatMemory)                  //  Chat Memory
                        // new VectorStoreChatMemoryAdvisor(vectorStore)),

                        //new QuestionAnswerAdvisor(vectorStore, SearchRequest.defaults()), // RAG
                        // 	.withFilterExpression("'documentType' == 'terms-of-service' && region in ['EU', 'US']")),
                        new SimpleLoggerAdvisor())
                .defaultFunctions("getInterviewSchedules","applyJob") // FUNCTION CALLING
                .build();

    }

    public String chat( String userMessageContent) {
        Account account= Utils.getAccount();
        return this.chatClient.prompt()
                .system(s -> s.param("current_date", LocalDate.now().toString()))
                .user(userMessageContent)
                .advisors(a -> a
                        .param(CHAT_MEMORY_CONVERSATION_ID_KEY, account.getId())
                        .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 100)
                        //.param(QuestionAnswerAdvisor.FILTER_EXPRESSION, "accountId == '" + account.getId() + "'") // Sửa phần bộ lọc
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
