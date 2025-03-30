package com.example.jobs_top.service;

import com.example.jobs_top.model.Job;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

@Service
public class ElasticService {
    private final VectorStore vectorStore;

    public ElasticService(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    public void addJobDocument(Job job){

    }
}
