package com.uade.tpo.demo.service.shoppingCart;

import com.uade.tpo.demo.entity.ShoppingCart;
import com.uade.tpo.demo.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ShoppingCartService {



   public List<ShoppingCart> getAllCarts();

   public Optional<ShoppingCart> getCartByUserId(Long userId);

   public ShoppingCart addProductToCart(Long userId, Product product, int quantity);

   public ShoppingCart updateProductInCart(Long userId, Product product);

   public void removeProductFromCart(Long userId, Long productId);

   public void clearCartByUserId(Long userId);

   public double calculateTotalPrice(Long userId);

    public ShoppingCart createCart(Long userId);
}
