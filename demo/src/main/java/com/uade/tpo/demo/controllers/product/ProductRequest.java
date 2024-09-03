package com.uade.tpo.demo.controllers.product;

import com.uade.tpo.demo.entity.Category;

import lombok.Data;

@Data
public class ProductRequest {
    private String description;
    private String model;
    private String genre;
    private String brand;
    private String size;
    private Integer stock;
    private Double price;
    private Category category;
}
