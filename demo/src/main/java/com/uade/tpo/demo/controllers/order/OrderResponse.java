package com.uade.tpo.demo.controllers.order;

import java.util.List;

import lombok.Data;

@Data
public class OrderResponse {
    private Long orderId;
    private String orderDate;
    private String paymentMethod;
    private String userName;
    private List<ProductDetail> products;
    private Double discount;
    private Double totalPrice;

    @Data
    public static class ProductDetail {
        private String model;
        private Integer quantity;
        private Double price;
    }
}

