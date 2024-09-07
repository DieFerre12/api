package com.uade.tpo.demo.controllers.shoppingCart;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class ShoppingCartRequest {
    private Long id;
    private List<ProductRequest> products;
    private Double totalPrice;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductRequest {
        private String id;
        private int quantity;
        private double price;
    }
}
