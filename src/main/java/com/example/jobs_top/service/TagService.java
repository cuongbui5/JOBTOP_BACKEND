package com.example.jobs_top.service;

import com.example.jobs_top.model.Tag;
import com.example.jobs_top.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {
    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }
}
