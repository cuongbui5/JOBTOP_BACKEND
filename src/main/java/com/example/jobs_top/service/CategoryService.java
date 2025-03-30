package com.example.jobs_top.service;

import com.example.jobs_top.model.Category;
import com.example.jobs_top.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category not found with id: " + id));
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category updateCategory(Long id, Category category) {
        Category existingCategory = getCategoryById(id);
        existingCategory.setName(category.getName());
        return categoryRepository.save(existingCategory);
    }

    public void deleteCategory(Long id) {
        Category existingCategory = getCategoryById(id);
        categoryRepository.delete(existingCategory);
    }

    public Category saveCategory(Category category) {
        Category existingCategory = categoryRepository.findByName(category.getName());
        if (existingCategory != null) {
            existingCategory.setName(category.getName());
            return categoryRepository.save(existingCategory);
        }
        return categoryRepository.save(category);
    }
}
