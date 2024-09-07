package com.uade.tpo.demo.service.product;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.uade.tpo.demo.entity.Brand;
import com.uade.tpo.demo.entity.Category;
import com.uade.tpo.demo.entity.Category.CategoryType;
import com.uade.tpo.demo.entity.Product;
import com.uade.tpo.demo.entity.Size;
import com.uade.tpo.demo.exceptions.InsufficientStockException;
import com.uade.tpo.demo.exceptions.InvalidPriceException;
import com.uade.tpo.demo.exceptions.InvalidProductDataException;

public interface ProductService {

    public Page<Product> getProducts(PageRequest pageRequest);

    public Optional<Product> getProductById(Long productId);
    
    public void deleteProduct(Long productId);
    
    public Product updateProduct(Long id, String name, String description, String genre, Double price, Integer stock) throws InvalidPriceException, InsufficientStockException;
    
    public Product createProduct(String description, String model, String genre, String image, Double price, Integer stock, 
    CategoryType categoryType, Brand brand, Size size) throws InvalidProductDataException, InvalidPriceException, InsufficientStockException;


}

