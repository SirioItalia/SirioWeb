package com.sirioitalia.api.controller;

import java.util.List;

import javax.validation.Valid;

import com.sirioitalia.api.exception.ResourceException;
import com.sirioitalia.api.model.Category;
import com.sirioitalia.api.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @GetMapping("/categories")
    public List<Category> getCategories() throws ResourceException {
        return categoryService.getCategories();
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<Category> getCategory(@PathVariable Long id) throws ResourceException {
        Category category = categoryService.getCategoryById(id);

        return new ResponseEntity<>(category, HttpStatus.FOUND);
    }

    @PostMapping("/categories")
    public ResponseEntity<Category> createCategory(@Valid @RequestBody Category categoryDetails) throws ResourceException {
        Category createdCategory = categoryService.createCategory(categoryDetails);

        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    @PutMapping("/categories/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @Valid @RequestBody Category categoryDetails)
            throws ResourceException {
        Category updatedCategory = categoryService.updateCategory(id, categoryDetails);

        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) throws ResourceException {
        categoryService.deleteCategory(id);

        return new ResponseEntity<>(HttpStatus.GONE);
    }

}
