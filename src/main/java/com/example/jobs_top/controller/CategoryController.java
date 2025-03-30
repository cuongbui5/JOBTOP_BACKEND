package com.example.jobs_top.controller;

import com.example.jobs_top.dto.res.ApiResponse;
import com.example.jobs_top.model.Category;
import com.example.jobs_top.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;


    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<?> getAllCategories() {
        return ResponseEntity.ok().body(new ApiResponse<>(
                200,
                "Success"
                ,categoryService.getAllCategories()));

    }

    @PostMapping("/save")
    public ResponseEntity<?> saveCategory(@RequestBody Category category) {
        return ResponseEntity.ok().body(new ApiResponse<>(
                200,
                "Success"
                ,categoryService.saveCategory(category)));

    }


}
