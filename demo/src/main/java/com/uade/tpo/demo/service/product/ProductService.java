package com.uade.tpo.demo.service.product;

import java.util.Optional;


import com.uade.tpo.demo.entity.Product;

public interface ProductService {
    Optional<Product> getProductById(Long productId);
    Optional<Product> getProductByName(String name);
    void deleteProduct(Long productId);
    Product updateProduct(Long productId, Product productDetails);
    Product saveProduct(String name, String description, String price, String stock);
    
}
