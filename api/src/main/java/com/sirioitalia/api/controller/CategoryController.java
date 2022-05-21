package com.sirioitalia.api.controller;

import com.sirioitalia.api.exception.ResourceException;
import com.sirioitalia.api.model.Category;
import com.sirioitalia.api.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<Category> getCategories() throws ResourceException {
        return categoryService.getCategories();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategory(@PathVariable Long id) throws ResourceException {
        Category category = categoryService.getCategoryById(id);

        return new ResponseEntity<>(category, HttpStatus.FOUND);
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@Valid @RequestBody Category categoryDetails) throws ResourceException {
        Category createdCategory = categoryService.createCategory(categoryDetails);

        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @Valid @RequestBody Category categoryDetails)
            throws ResourceException {
        Category updatedCategory = categoryService.updateCategory(id, categoryDetails);

        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) throws ResourceException {
        categoryService.deleteCategory(id);

        return new ResponseEntity<>(HttpStatus.GONE);
    }

}
