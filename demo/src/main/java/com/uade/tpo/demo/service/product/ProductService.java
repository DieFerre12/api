package com.uade.tpo.demo.service.product;

import java.util.Optional;


import com.uade.tpo.demo.entity.Product;

public interface ProductService {
    public Optional<Product> getProductById(Long productId);

    public Optional<Product> getProductByName(String name);
    
    public void deleteProduct(Long productId);
    
    public Product updateProduct(Long productId, Product productDetails);
    
    public Product saveProduct(String name, String description, String price, String stock);

}
