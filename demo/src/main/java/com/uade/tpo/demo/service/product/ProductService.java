package com.uade.tpo.demo.service.product;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.uade.tpo.demo.entity.Product;

public interface ProductService {

    public Page<Product> getProducts(PageRequest pageRequest);

    public Optional<Product> getProductById(Long productId);

    public Optional<Product> getProductByName(String name);
    
    public void deleteProduct(Long productId);
    
    public Product updateProduct(Long productId, Product productDetails);
    
    public Object updateProduct(Long productId, String name, String description, String price, String stock);

    public Product createProduct(String genre, String description, Double price, Integer stock);

}
