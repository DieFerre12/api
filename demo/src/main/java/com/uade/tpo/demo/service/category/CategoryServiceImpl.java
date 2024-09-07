package com.uade.tpo.demo.service.category;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.uade.tpo.demo.entity.Category;
import com.uade.tpo.demo.entity.Category.CategoryType;
import com.uade.tpo.demo.exceptions.CategoryDuplicateException;
import com.uade.tpo.demo.exceptions.CategoryNotFoundException;
import com.uade.tpo.demo.repository.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Page<Category> getCategories(PageRequest pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Override
    public Category getCategoryById(Long categoryId) throws CategoryNotFoundException {
        // Lanza CategoryNotFoundException si no encuentra la categoría por ID
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException());
    }

    @Override
    public Category getCategoryByType(CategoryType categoryType) throws CategoryNotFoundException {
        // Lanza CategoryNotFoundException si no encuentra la categoría por tipo
        return categoryRepository.findByCategoryType(categoryType)
                .orElseThrow(() -> new CategoryNotFoundException());
    }

    @Override
    public Category createCategory(CategoryType categoryType) throws CategoryDuplicateException {
        // Verifica si la categoría ya existe
        Optional<Category> existingCategory = categoryRepository.findByCategoryType(categoryType);
        if (existingCategory.isPresent()) {
            // Si la categoría ya existe, lanza CategoryDuplicateException
            throw new CategoryDuplicateException();
        }
        // Si no existe, crea la nueva categoría
        Category newCategory = new Category();
        newCategory.setCategoryType(categoryType);
        return categoryRepository.save(newCategory);
    }
}
