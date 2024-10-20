package com.uade.tpo.demo.service.category;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.uade.tpo.demo.entity.Category;
import com.uade.tpo.demo.entity.Category.CategoryType;
import com.uade.tpo.demo.exceptions.CategoryDuplicateException;
import com.uade.tpo.demo.exceptions.CategoryNotFoundException;

public interface CategoryService {
    public Page<Category> getCategories(PageRequest pageRequest);

    public Category getCategoryById(Long categoryId) throws CategoryDuplicateException, CategoryNotFoundException;

    public Category getCategoryByType(CategoryType categoryType) throws CategoryDuplicateException, CategoryNotFoundException;

    public Category createCategory(CategoryType categoryType) throws CategoryDuplicateException;

    
}
