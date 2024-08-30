package com.uade.tpo.demo.repository;

import com.uade.tpo.demo.entity.Product;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByName(String name);
    Optional<Product> findById(Long productId);
    void deleteById(Long productId);
    
}
