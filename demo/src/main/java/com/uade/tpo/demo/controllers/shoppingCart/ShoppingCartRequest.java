package com.uade.tpo.demo.controllers.shoppingCart;

import java.util.List;

import com.uade.tpo.demo.entity.User;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ShoppingCartRequest {
    private Long id;
    private List<ProductRequest> products;
    private Double totalPrice;
    private User user;

    @Data
    @NoArgsConstructor
    public static class ProductRequest {
        private Long id;
        private String model;  // Añade nombre si quieres mostrarlo
        private double price;

        public ProductRequest(Long id, String model, double price) {
            this.id = id;
            this.model = model;
            this.price = price;
        }
    }
}
