package com.uade.tpo.demo.service.shoppingCart;

import com.uade.tpo.demo.entity.ShoppingCart;
import com.uade.tpo.demo.entity.Size;
import com.uade.tpo.demo.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ShoppingCartService {

   public List<ShoppingCart> getAllCarts();

   public Optional<ShoppingCart> getCartByUserId(Long userId);

   public ShoppingCart addProductToCart(Long userId, Product product, int quantity, String model, Size size);

   public ShoppingCart updateProductInCart(Long userId, Product product, int quantity,Size size,String model);

   public void removeProductFromCart(Long userId, Size size, String model);

   public void clearCartByUserId(Long userId);

   public double calculateTotalPrice(Long userId);

    public ShoppingCart createCart(Long userId);
    
    void updateProductStock(Product product);
}
