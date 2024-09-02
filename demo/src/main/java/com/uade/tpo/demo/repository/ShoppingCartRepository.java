package com.uade.tpo.demo.repository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import com.uade.tpo.demo.entity.ShoppingCart;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    Optional<ShoppingCart> findByUserId(Long userId);
}
