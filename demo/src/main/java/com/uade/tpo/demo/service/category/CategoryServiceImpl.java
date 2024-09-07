package com.uade.tpo.demo.service.category;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.uade.tpo.demo.entity.Category;
import com.uade.tpo.demo.entity.Category.CategoryType;
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
    public Optional<Category> getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId);
    }

    @Override
    public Optional<Category> getCategoryByType(CategoryType categoryType) {
        return categoryRepository.findByCategoryType(categoryType);  
    }

    @Override
    public Optional<Category> createCategory(CategoryType categoryType) {
        Category category = new Category();
        category.setCategoryType(categoryType);
        return Optional.of(categoryRepository.save(category));
    }
}
