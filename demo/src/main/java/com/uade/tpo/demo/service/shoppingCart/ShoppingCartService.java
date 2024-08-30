package com.uade.tpo.demo.service.shoppingCart;

import com.uade.tpo.demo.entity.ShoppingCart;
import com.uade.tpo.demo.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ShoppingCartService {

    List<ShoppingCart> getAllCarts();

    Optional<ShoppingCart> getCartByUserId(Long userId);

    ShoppingCart addOrUpdateCart(ShoppingCart shoppingCart);

    ShoppingCart addProductToCart(Long userId, Product product);

    ShoppingCart updateProductInCart(Long userId, Product product);

    void removeProductFromCart(Long userId, Long productId);

    void clearCartByUserId(Long userId);

    double calculateTotalPrice(Long userId);
}
