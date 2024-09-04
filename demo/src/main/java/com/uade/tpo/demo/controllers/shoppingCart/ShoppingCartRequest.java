package com.uade.tpo.demo.controllers.shoppingCart;

import java.util.List;

import com.uade.tpo.demo.entity.Product;

import lombok.Data;

@Data
public class ShoppingCartRequest {
    private Long Id;
    private List<Product> products;
    private Integer quantity; 
    private Double totalPrice; 
}

