package com.uade.tpo.demo.controllers.shoppingCart;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uade.tpo.demo.entity.Product;
import com.uade.tpo.demo.entity.ShoppingCart;
import com.uade.tpo.demo.service.shoppingCart.ShoppingCartService;

@RestController
@RequestMapping("shoppingCart")
public class ShoppingCartController {

    @Autowired ShoppingCartService shoppingCartService;
    
    @GetMapping public ResponseEntity<List<ShoppingCart>> getAllCarts() {
        return ResponseEntity.ok(shoppingCartService.getAllCarts());
    }

    @GetMapping public ResponseEntity<ShoppingCart> getCartByUserId(Long userId) {
        return shoppingCartService.getCartByUserId(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping public ResponseEntity<ShoppingCart> addProductToCart(Long userId, Product productId) {
        return ResponseEntity.ok(shoppingCartService.addProductToCart(userId, productId));
    }

    @GetMapping public ResponseEntity<ShoppingCart> updateProductInCart(Long userId, Product productId) {
        return ResponseEntity.ok(shoppingCartService.updateProductInCart(userId, productId));
    }

    @PostMapping public ResponseEntity<Object> createCart(Long userId) {
        return ResponseEntity.ok(shoppingCartService.createCart(userId));
    }
}
