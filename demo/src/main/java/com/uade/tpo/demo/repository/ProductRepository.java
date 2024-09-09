package com.uade.tpo.demo.repository;

import com.uade.tpo.demo.entity.Product;
import com.uade.tpo.demo.entity.Category;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByCategory(Category category);
}
