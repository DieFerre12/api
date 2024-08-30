package com.uade.tpo.demo.service.category;
/* 
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.uade.tpo.demo.entity.Product;
import com.uade.tpo.demo.exceptions.InsufficientStockException;
import com.uade.tpo.demo.exceptions.ProductNotFoundException;
import com.uade.tpo.demo.repository.ProductRepository;
import com.uade.tpo.demo.service.product.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Page<Product> getProducts(PageRequest pageable) {
        return productRepository.findAll(pageable);
    }

    public Optional<Product> getProductById(Long productId) {
        return productRepository.findById(productId);
    }

    public Product updateStock(Long productId, int quantity) throws InsufficientStockException {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            if (product.getStock() >= quantity) {
                product.setStock(product.getStock() - quantity);
                return productRepository.save(product);
            } else {
                throw new InsufficientStockException();
            }
        } else {
            throw new ProductNotFoundException(); // Asumiendo que tienes esta excepción para cuando no se encuentra el producto
        }
    }
}
*/

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.uade.tpo.demo.entity.Product;
import com.uade.tpo.demo.exceptions.InsufficientStockException;
import com.uade.tpo.demo.exceptions.InvalidPriceException;
import com.uade.tpo.demo.exceptions.ProductNotFoundException;
import com.uade.tpo.demo.repository.ProductRepository;
import com.uade.tpo.demo.service.product.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Page<Product> getProducts(PageRequest pageable) {
        return productRepository.findAll(pageable);
    }

    public Optional<Product> getProductById(Long productId) {
        return productRepository.findById(productId);
    }

    public Product updateStock(Long productId, int quantity) throws InsufficientStockException, ProductNotFoundException {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            if (product.getStock() >= quantity) {
                product.setStock(product.getStock() - quantity);
                return productRepository.save(product);
            } else {
                throw new InsufficientStockException();
            }
        } else {
            throw new ProductNotFoundException(); // Asumiendo que tienes esta excepción para cuando no se encuentra el producto
        }
    }

    public Product createProduct(Product product) throws InvalidPriceException {
        validatePrice(product.getPrice());
        return productRepository.save(product);
    }

    public Product updateProduct(Long productId, Product product) throws ProductNotFoundException {
        if (!productRepository.existsById(productId)) {
            throw new ProductNotFoundException(); // Asumiendo que tienes esta excepción para cuando no se encuentra el producto
        }
        validatePrice(product.getPrice());
        product.setId(productId); // Asignar el ID del producto que se está actualizando
        return productRepository.save(product);
    }

    private void validatePrice(Double price) throws InvalidPriceException {
        if (price == null || price <= 0) {
            throw new InvalidPriceException();
        }
    }
}
