package com.uade.tpo.demo.controllers.product;

import java.util.Map;

import com.uade.tpo.demo.entity.Brand;
import com.uade.tpo.demo.entity.Size;
import com.uade.tpo.demo.entity.Category.CategoryType;
import lombok.Data;

@Data
public class ProductRequest {
    private String description;
    private String model;
    private String genre;
    private Long imageId;
    private Brand brand;
    private Map<Size, Integer> size;  // Mapa de tallas y stock
    private Double price;
    private CategoryType categoryType;
}


