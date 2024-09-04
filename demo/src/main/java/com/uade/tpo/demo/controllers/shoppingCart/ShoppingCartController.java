package com.uade.tpo.demo.controllers.shoppingCart;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uade.tpo.demo.entity.Product;
import com.uade.tpo.demo.entity.ShoppingCart;
import com.uade.tpo.demo.service.shoppingCart.ShoppingCartService;

@RestController
@RequestMapping("/ShoppingCart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @GetMapping
    public List<ShoppingCart> getAllCarts() {
        return shoppingCartService.getAllCarts();
    }

    @GetMapping("/{userId}")
    public Optional<ShoppingCart> getCartByUserId(@PathVariable Long userId) {
        return shoppingCartService.getCartByUserId(userId);
    }

    @PostMapping("/{userId}/add")
    public ShoppingCart addProductToCart(@PathVariable Long userId, @RequestBody Product product) {
        return shoppingCartService.addProductToCart(userId, product);
    }

    @PutMapping("/{userId}/update")
    public ShoppingCart updateProductInCart(@PathVariable Long userId, @RequestBody Product product) {
        return shoppingCartService.updateProductInCart(userId, product);
    }

    @DeleteMapping("/{userId}/remove/{productId}")
    public void removeProductFromCart(@PathVariable Long userId, @PathVariable Long productId) {
        shoppingCartService.removeProductFromCart(userId, productId);
    }

    @DeleteMapping("/{userId}/clear")
    public void clearCartByUserId(@PathVariable Long userId) {
        shoppingCartService.clearCartByUserId(userId);
    }

    @GetMapping("/{userId}/total-price")
    public double calculateTotalPrice(@PathVariable Long userId) {
        return shoppingCartService.calculateTotalPrice(userId);
    }

    @PostMapping("/{userId}/create")
    public ShoppingCart createCart(@PathVariable Long userId) {
        return shoppingCartService.createCart(userId);
    }
}
