package com.example.jobs_top.init;

import com.example.jobs_top.service.CustomerSupportAssistant;
//import org.springframework.ai.ollama.OllamaChatModel;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
public class Test implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {

    }

    /*private final OllamaChatModel chatModel;
    private final CustomerSupportAssistant customerSupportAssistant;

    public Test(OllamaChatModel chatModel, CustomerSupportAssistant customerSupportAssistant) {
        this.chatModel = chatModel;
        this.customerSupportAssistant = customerSupportAssistant;
    }

    @Override
    public void run(String... args) throws Exception {
        //String result=chatModel.call("Bạn là ai?");
        //String result=customerSupportAssistant.chat("1","Bạn là ai ?");
        //String result=customerSupportAssistant.chat("1","Tôi muốn xem thông tin về đơn ứng tuyển của tôi");
        //System.out.println(result);
    }*/
}
