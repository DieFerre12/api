package com.uade.tpo.demo.service.product;

import java.util.Optional;

import java.util.List;
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

    public Product createProduct(String description, String model, String genre, Long imageId, Double price, Integer stock, 

    CategoryType categoryType, Brand brand, Size size) throws InvalidProductDataException, InvalidPriceException, InsufficientStockException;

    public List<Product> findByCategoryType(CategoryType categoryType);

    public List<Product> getProductByModel(String model);

    public Optional<Product> getProductByModelAndSize(String model, Size size);


}

