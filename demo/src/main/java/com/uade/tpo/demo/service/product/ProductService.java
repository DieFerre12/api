package com.uade.tpo.demo.service.product;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.uade.tpo.demo.entity.Product;
import com.uade.tpo.demo.exceptions.InsufficientStockException;
import com.uade.tpo.demo.exceptions.InvalidPriceException;
import com.uade.tpo.demo.exceptions.InvalidProductDataException;

public interface ProductService {

    public Page<Product> getProducts(PageRequest pageRequest);

    public Optional<Product> getProductById(Long productId);

    public Optional<Product> getProductByName(String name);
    
    public void deleteProduct(Long productId);
    
    public Product updateProduct(Long productId, String name, String description, Double price, Integer stock) throws InvalidPriceException, InsufficientStockException;

    public Product createProduct(String genre, String description, Double price, Integer stock) throws InvalidProductDataException,InvalidPriceException,InsufficientStockException;

}
