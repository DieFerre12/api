package com.uade.tpo.demo.controllers.product;

import lombok.Data;

@Data
public class ProductRequest {
    private String description;
    private String model;
    private String genre;
    private String brand;
    private String color;
    private String size;
    private Integer stock;
    private Double price;
}
