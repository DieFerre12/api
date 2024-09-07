package com.uade.tpo.demo.service.shoppingCart;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uade.tpo.demo.entity.Product;
import com.uade.tpo.demo.entity.ShoppingCart;
import com.uade.tpo.demo.repository.ShoppingCartRepository;
import com.uade.tpo.demo.repository.UserRepository;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private UserRepository userRepository;

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
        ShoppingCart cart = getCartByUserId(userId).orElseGet(() -> createCart(userId));
        cart.getProducts().add(product);
        return shoppingCartRepository.save(cart);
    }

    @Override
    public ShoppingCart updateProductInCart(Long userId, Product product) {
        ShoppingCart cart = getCartByUserId(userId).orElseThrow(() -> new RuntimeException("Carrito no encontrado"));
        // Implement logic to update the product in the cart
        return shoppingCartRepository.save(cart);
    }

    @Override
    public void removeProductFromCart(Long userId, Long productId) {
        ShoppingCart cart = getCartByUserId(userId).orElseThrow(() -> new RuntimeException("Carrito no encontrado"));
        cart.getProducts().removeIf(p -> p.getId().equals(productId));
        shoppingCartRepository.save(cart);
    }

    @Override
    public void clearCartByUserId(Long userId) {
        ShoppingCart cart = getCartByUserId(userId).orElseThrow(() -> new RuntimeException("Carrito no encontrado"));
        cart.getProducts().clear();
        shoppingCartRepository.save(cart);
    }

    @Override
    public double calculateTotalPrice(Long userId) {
        ShoppingCart cart = getCartByUserId(userId).orElseThrow(() -> new RuntimeException("Carrito no encontrado"));
        return cart.getProducts().stream().mapToDouble(Product::getPrice).sum();
    }

    @Override
    public ShoppingCart createCart(Long userId) {
        ShoppingCart cart = new ShoppingCart();
        cart.setUser(userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Usuario no encontrado")));
        return shoppingCartRepository.save(cart);
    }
}
