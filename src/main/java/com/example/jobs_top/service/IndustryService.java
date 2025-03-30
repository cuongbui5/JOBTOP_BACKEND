package com.example.jobs_top.service;

import com.example.jobs_top.model.Industry;
import com.example.jobs_top.repository.IndustryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndustryService {
    private final IndustryRepository industryRepository;

    public IndustryService(IndustryRepository industryRepository) {
        this.industryRepository = industryRepository;
    }

    public List<Industry> getAll() {
        return industryRepository.findAll();
    }

    public Industry save(Industry industry) {
        return industryRepository.save(industry);
    }
}
