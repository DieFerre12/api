package com.uade.tpo.demo.service.product;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.uade.tpo.demo.entity.Product;
import com.uade.tpo.demo.exceptions.InsufficientStockException;
import com.uade.tpo.demo.exceptions.InvalidPriceException;
import com.uade.tpo.demo.exceptions.InvalidProductDataException;
import com.uade.tpo.demo.repository.ProductRepository;

public class ProductServiceImpl implements ProductService {


    @Autowired
    private ProductRepository productRepository;

    @Override
    public Optional<Product> getProductById(Long productId) {
        return productRepository.findById(productId);
    }

    @Override
    public Optional<Product> getProductByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }

    @Override
    public Product createProduct(String name, String description, Double price, Integer stock) throws InvalidProductDataException, InvalidPriceException, InsufficientStockException {
        if (name == null || name.isEmpty() || description == null || description.isEmpty()) {
            throw new InvalidProductDataException();
        }
        if (price <= 10000) {
            throw new InvalidPriceException();
        }
        if (stock < 0) {
            throw new InsufficientStockException();
        }
        Optional <Product> products = productRepository.findByName(name);
        if (products.isEmpty()){
            return productRepository.save(new Product());
        }
        throw new RuntimeException("El Producto ya existe");
    }

    @Override
    public Page<Product> getProducts(PageRequest pageRequest) {
        return productRepository.findAll(pageRequest);
    }

    @Override
    public Product updateProduct(Long productId, String genre, String description, Double price, Integer stock) throws InvalidPriceException, InsufficientStockException {
            if (price <= 10000) {
                throw new InvalidPriceException();
            }
            if (stock < 0) {
                throw new InsufficientStockException(); 
            }
            return productRepository.findById(productId).map(product -> {
            product.setGenre(genre);
            product.setDescription(description);
            product.setPrice(price);
            product.setStock(stock);
            return productRepository.save(product);
        }).orElseThrow(() -> new RuntimeException("Producto no encontrado "));
    }

    
}