package com.uade.tpo.demo.repository;

import java.util.List;

import com.uade.tpo.demo.entity.CartItem;
import com.uade.tpo.demo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByProduct(Product product);
}
