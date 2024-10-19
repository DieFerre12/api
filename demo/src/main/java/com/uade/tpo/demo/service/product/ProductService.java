package com.uade.tpo.demo.service.product;

import java.util.Optional;
import java.sql.Blob;
import java.util.List;

import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import com.uade.tpo.demo.entity.Brand;
import com.uade.tpo.demo.entity.Category.CategoryType;
import com.uade.tpo.demo.entity.Product;
import com.uade.tpo.demo.entity.Size;
import com.uade.tpo.demo.exceptions.InsufficientStockException;
import com.uade.tpo.demo.exceptions.InvalidPriceException;
import com.uade.tpo.demo.exceptions.InvalidProductDataException;

public interface ProductService {

    public Page<Product> getProducts(PageRequest pageRequest);

    public Optional<Product> getProductById(Long productId);
    
    public void deleteProduct(String model);
    
    public Product updateProductSize(String model,Size size, Integer stock) throws InsufficientStockException;
    
    public List<Product> updateProductPrice(String model, Double price) throws InvalidPriceException;

    public List<Product> createProduct(String description, String model, String genre, Blob image, Double price,
    Map<Size, Integer> sizeStockMap, CategoryType categoryType, Brand brand) throws InvalidProductDataException, InvalidPriceException, InsufficientStockException;

    public List<Product> findByCategoryType(CategoryType categoryType);

    public List<Product> getProductByModel(String model);

    public Optional<Product> getProductByModelAndSize(String model, Size size);

    public List<Product> findByBrand(Brand brand);

}

