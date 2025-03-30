package com.example.jobs_top.controller;

import com.example.jobs_top.dto.res.ApiResponse;
import com.example.jobs_top.model.Tag;
import com.example.jobs_top.service.TagService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {
    private final TagService tagService;


    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public ResponseEntity<?> getTags() {
        return ResponseEntity.ok().body(
                new ApiResponse<>(200,"success",tagService.getAllTags())
        );
    }

    @PostMapping
    public ResponseEntity<?> createTag(@RequestBody Tag tag) {
        return ResponseEntity.ok().body(
                new ApiResponse<>(200,"success",tagService.saveTag(tag))
        );
    }
}
