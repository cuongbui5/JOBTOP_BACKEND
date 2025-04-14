package com.example.jobs_top.config;

import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    @Bean
    public TokenTextSplitter tokenTextSplitter() {
        return new TokenTextSplitter();
    }
}
