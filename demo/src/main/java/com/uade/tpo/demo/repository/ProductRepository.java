package com.uade.tpo.demo.repository;

import com.uade.tpo.demo.entity.Product;
import com.uade.tpo.demo.entity.Size;
import com.uade.tpo.demo.entity.Category;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory(Category category);

    Optional<Product> findByModelAndSize(String model, Size size);
    
    List<Product> findByModel(String model);

    Optional<Product> findByModelAndSizeAndCategory(String model, Size size, Category category);

    

    
}
