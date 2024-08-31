package com.uade.tpo.demo.service.shoppingCart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uade.tpo.demo.entity.ShoppingCart;
import com.uade.tpo.demo.entity.Category;
import com.uade.tpo.demo.entity.Product;
import com.uade.tpo.demo.repository.ShoppingCartRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Override
    public List<ShoppingCart> getAllCarts() {
        return shoppingCartRepository.findAll();
    }

    @Override
    public Optional<ShoppingCart> getCartByUserId(Long userId) {
        return shoppingCartRepository.findByUserId(userId);
    }

    @Override
    public ShoppingCart addProductToCart(Long userId, Product product) {
        ShoppingCart cart = shoppingCartRepository.findByUserId(userId).orElse(new ShoppingCart());
        cart.getProducts().add(product);
        cart.setTotalPrice(cart.getTotalPrice() + product.getPrice());
        return shoppingCartRepository.save(cart);
    }

    @Override
    public ShoppingCart updateProductInCart(Long userId, Product product) {
        ShoppingCart cart = shoppingCartRepository.findByUserId(userId)
            .orElseThrow(() -> new RuntimeException("Cart not found"));
        cart.getProducts().stream()
            .filter(p -> p.getId().equals(product.getId()))
            .findFirst()
            .ifPresent(existingProduct -> {
                cart.setTotalPrice(cart.getTotalPrice() - existingProduct.getPrice() + product.getPrice());
                existingProduct.setPrice(product.getPrice());
                existingProduct.setGenre(product.getGenre());
            });
        return shoppingCartRepository.save(cart);
    }

    @Override
    public void removeProductFromCart(Long userId, Long productId) {
        ShoppingCart cart = shoppingCartRepository.findByUserId(userId)
            .orElseThrow(() -> new RuntimeException("Cart not found"));
        cart.getProducts().removeIf(p -> p.getId().equals(productId));
        cart.setTotalPrice(cart.getProducts().stream().mapToDouble(Product::getPrice).sum());
        shoppingCartRepository.save(cart);
    }

    @Override
    public void clearCartByUserId(Long userId) {
        ShoppingCart cart = shoppingCartRepository.findByUserId(userId)
            .orElseThrow(() -> new RuntimeException("Cart not found"));
        cart.getProducts().clear();
        cart.setTotalPrice(0.0);
        shoppingCartRepository.save(cart);
    }

    @Override
    public double calculateTotalPrice(Long userId) {
        ShoppingCart cart = shoppingCartRepository.findByUserId(userId)
            .orElseThrow(() -> new RuntimeException("Cart not found"));
        return cart.getProducts().stream().mapToDouble(Product::getPrice).sum();
    }

    @Override
public ShoppingCart createCart(Long userId) {
    Optional<ShoppingCart> existingCart = shoppingCartRepository.findByUserId(userId);
    if (existingCart.isPresent()) {
        return existingCart.get();
    } else {
        ShoppingCart newCart = ShoppingCart.builder()
                .userId(userId)
                .totalPrice(0.0)
                .build();
        return shoppingCartRepository.save(newCart);
    }
}

}