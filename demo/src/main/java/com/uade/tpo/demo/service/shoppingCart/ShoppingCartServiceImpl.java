package com.uade.tpo.demo.service.shoppingCart;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uade.tpo.demo.entity.CartItem;
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
    public ShoppingCart addProductToCart(Long userId, Product product, int quantity) {
        ShoppingCart cart = getCartByUserId(userId).orElseGet(() -> createCart(userId));

        // Verificar si hay suficiente stock
        if (product.getStock() < quantity) {
            throw new IllegalArgumentException("No hay suficiente stock para el producto: " + product.getModel());
        }

        // Busca si el producto ya está en el carrito
        CartItem item = cart.getItems().stream()
            .filter(i -> i.getProduct().getId().equals(product.getId()))
            .findFirst()
            .orElse(null);

        if (item == null) {
            // Si el producto no está en el carrito, añade uno nuevo con la cantidad especificada
            item = new CartItem();
            item.setProduct(product);
            item.setQuantity(quantity);  // Aquí se utiliza la cantidad correcta
            item.setShoppingCart(cart);
            cart.getItems().add(item);
        } else {
            // Si el producto ya está en el carrito, actualiza la cantidad
            item.setQuantity(item.getQuantity() + quantity);
        }

        // Restar la cantidad del stock del producto
        product.setStock(product.getStock() - quantity);

        return shoppingCartRepository.save(cart);
    }

    @Override
    public ShoppingCart updateProductInCart(Long userId, Product product) {
        ShoppingCart cart = getCartByUserId(userId).orElseThrow(() -> new RuntimeException("Carrito no encontrado"));
        cart.addProduct(product, 1);  // Update logic for quantity if needed
        return shoppingCartRepository.save(cart);
    }

    @Override
    public void removeProductFromCart(Long userId, Long productId) {
        ShoppingCart cart = getCartByUserId(userId).orElseThrow(() -> new RuntimeException("Carrito no encontrado"));
        cart.getItems().removeIf(item -> item.getProduct().getId().equals(productId));
        shoppingCartRepository.save(cart);
    }

    @Override
    public void clearCartByUserId(Long userId) {
        ShoppingCart cart = getCartByUserId(userId).orElseThrow(() -> new RuntimeException("Carrito no encontrado"));
        cart.getItems().clear();
        shoppingCartRepository.save(cart);
    }

    @Override
    public double calculateTotalPrice(Long userId) {
        ShoppingCart cart = getCartByUserId(userId).orElseThrow(() -> new RuntimeException("Carrito no encontrado"));
        return cart.getTotalPrice();
    }

    @Override
    public ShoppingCart createCart(Long userId) {
        ShoppingCart cart = new ShoppingCart();
        cart.setUser(userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Usuario no encontrado")));
        return shoppingCartRepository.save(cart);
    }

}
