package com.uade.tpo.demo.controllers.shoppingCart;

import java.util.List;

import com.uade.tpo.demo.controllers.user.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ShoppingCartRequest {
    private Long id;
    private List<ProductRequest> products;
    private Double totalPrice;
    private UserResponse userResponse;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProductRequest {
        private Long id;
        private String model;
        private double price;
        private int quantity;  
    }
    
}
