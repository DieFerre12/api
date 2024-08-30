package com.uade.tpo.demo.repository;

import org.springframework.stereotype.Repository;
import java.util.Optional;
import com.uade.tpo.demo.entity.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long>{
    Optional<ShoppingCart> findByUserId(Long userId);
    Optional<ShoppingCart> findByShoppingCartId(Long shoppingCartId);
    void deleteByUserId(Long userId);
    void deleteByShoppingCartId(Long shoppingCartId);
}
