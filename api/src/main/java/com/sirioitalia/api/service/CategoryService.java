package com.sirioitalia.api.service;

import com.sirioitalia.api.exception.ResourceException;
import com.sirioitalia.api.model.Category;
import com.sirioitalia.api.repository.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {
    private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);
    private CategoryRepository categoryRepository;

    public CategoryService() {
        super();
    }


    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        super();
        this.categoryRepository = categoryRepository;
    }


    public Category getCategoryById(Long categoryId) throws ResourceException {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceException("404", "Category not found", HttpStatus.NOT_FOUND));
    }


    public List<Category> getCategories() {
        return (List<Category>) categoryRepository.findAll();
    }


    @Transactional
    public Category updateCategory(Long categoryId, Category categoryDetails) throws ResourceException {
        Category categoryToUpdate = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceException("404", "Category not found", HttpStatus.NOT_FOUND));

        categoryToUpdate.setLabel(categoryDetails.getLabel());
        return categoryRepository.save(categoryToUpdate);
    }


    @Transactional
    public Category createCategory(Category category) throws ResourceException {
        return categoryRepository.save(category);
    }


    @Transactional
    public void deleteCategory(Long categoryId) throws ResourceException {
        Category categoryToDelete = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceException("404", "Category not found", HttpStatus.NOT_FOUND));

        categoryRepository.delete(categoryToDelete);
    }
}
