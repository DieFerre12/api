package com.uade.tpo.demo.service.product;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.uade.tpo.demo.entity.Product;
import com.uade.tpo.demo.exceptions.InsufficientStockException;
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
    public Product updateProduct(Long productId, Product productDetails) {
        return productRepository.findById(productId).map(product -> {
            product.setGenre(productDetails.getGenre());
            product.setDescription(productDetails.getDescription());
            product.setPrice(productDetails.getPrice());
            product.setStock(productDetails.getStock());
            return productRepository.save(product);
        }).orElseThrow(() -> new RuntimeException("Product not found"));    
    }

    @Override
    public Product createProduct(String name, String description, Double price, Integer stock) {
        Optional <Product> products = productRepository.findByName(name);
        if (products.isEmpty())
            return productRepository.save(new Product());
        throw new RuntimeException("Product already exists");
    }

    @Override
    public Page<Product> getProducts(PageRequest pageRequest) {
        return productRepository.findAll(pageRequest);
    }

    @Override
    public Product updateProduct(Long productId, String genre, String description, String price, String stock) {
        return productRepository.findById(productId).map(product -> {
            if (stock < 0) {
                throw new InsufficientStockException("El stock no puede ser negativo."); //ultimo agregado
            }
            product.setGenre(product.getGenre());
            product.setDescription(product.getDescription());
            product.setPrice(product.getPrice());
            product.setStock(product.getStock());
            return productRepository.save(product);
        }).orElseThrow(() -> new RuntimeException("Producto no encontrado "));
    }
    
}
