package com.uade.tpo.demo.controllers.categories;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uade.tpo.demo.entity.Category;
import com.uade.tpo.demo.entity.Category.CategoryType;
import com.uade.tpo.demo.exceptions.CategoryDuplicateException;
import com.uade.tpo.demo.exceptions.CategoryNotFoundException;
import com.uade.tpo.demo.service.category.CategoryService;

@RestController
@RequestMapping("/categories")
public class CategoriesController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<Page<Category>> getCategories(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        if (page == null || size == null)
            return ResponseEntity.ok(categoryService.getCategories(PageRequest.of(0, Integer.MAX_VALUE)));
        return ResponseEntity.ok(categoryService.getCategories(PageRequest.of(page, size)));
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<Object> getCategoryById(@PathVariable Long categoryId) {
        try {
            Category result = categoryService.getCategoryById(categoryId);
            return ResponseEntity.ok(result.getId());
        } catch (CategoryNotFoundException | CategoryDuplicateException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Categoría no encontrada.");
        }
    }

    @PostMapping
    public ResponseEntity<Object> selectCategory(@RequestBody CategoryType categoryType) {
        try {
            Category result = categoryService.getCategoryByType(categoryType);
            return ResponseEntity.ok(result.getId());
        } catch (CategoryNotFoundException | CategoryDuplicateException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La categoría no existe.");
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createCategory(@RequestBody CategoryRequest categoryRequest)
            throws CategoryDuplicateException {
        // El servicio lanza CategoryDuplicateException si la categoría ya existe
        Category result = categoryService.createCategory(categoryRequest.getCategoryType());
        return ResponseEntity.ok(result.getId());
    }

}
