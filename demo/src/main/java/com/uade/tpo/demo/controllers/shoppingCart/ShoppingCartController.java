package com.uade.tpo.demo.controllers.shoppingCart;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uade.tpo.demo.entity.Product;
import com.uade.tpo.demo.entity.ShoppingCart;
import com.uade.tpo.demo.service.shoppingCart.ShoppingCartService;

@RestController
@RequestMapping("shoppingCart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @GetMapping
    public ResponseEntity<List<ShoppingCart>> getAllCarts() {
        return ResponseEntity.ok(shoppingCartService.getAllCarts());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ShoppingCart> getCartByUserId(@PathVariable Long userId) {
        return shoppingCartService.getCartByUserId(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{userId}/addProduct")
    public ResponseEntity<ShoppingCart> addProductToCart(@PathVariable Long userId, @RequestBody Product product) {
        return ResponseEntity.ok(shoppingCartService.addProductToCart(userId, product));
    }

    @PutMapping("/{userId}/updateProduct")
    public ResponseEntity<ShoppingCart> updateProductInCart(@PathVariable Long userId, @RequestBody Product product) {
        return ResponseEntity.ok(shoppingCartService.updateProductInCart(userId, product));
    }

    @DeleteMapping("/{userId}/removeProduct/{productId}")
    public ResponseEntity<Void> removeProductFromCart(@PathVariable Long userId, @PathVariable Long productId) {
        shoppingCartService.removeProductFromCart(userId, productId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{userId}/clearCart")
    public ResponseEntity<Void> clearCartByUserId(@PathVariable Long userId) {
        shoppingCartService.clearCartByUserId(userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{userId}/totalPrice")
    public ResponseEntity<Double> calculateTotalPrice(@PathVariable Long userId) {
        return ResponseEntity.ok(shoppingCartService.calculateTotalPrice(userId));
    }

    @PostMapping("/createCart")
    public ResponseEntity<ShoppingCart> createCart(@RequestParam Long userId) {
        return ResponseEntity.ok(shoppingCartService.createCart(userId));
    }
}