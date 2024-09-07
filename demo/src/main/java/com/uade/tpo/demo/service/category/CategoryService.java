package com.uade.tpo.demo.service.category;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.uade.tpo.demo.entity.Category;
import com.uade.tpo.demo.entity.Category.CategoryType;

public interface CategoryService {
    public Page<Category> getCategories(PageRequest pageRequest);

    public Optional<Category> getCategoryById(Long categoryId);

    public Optional<Category> getCategoryByType(CategoryType categoryType);

    public Optional<Category> createCategory(CategoryType categoryType);

    
}
