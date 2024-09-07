package com.uade.tpo.demo.controllers.order;


import lombok.Data;

@Data
public class OrderRequest {
    private Long id;
    private String orderDate;
    private String paymentMethod;
}
