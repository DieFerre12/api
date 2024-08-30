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
    
    public Product saveProduct(String name, String description, String price, String stock);

}
