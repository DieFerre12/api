package com.uade.tpo.demo.service.product;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.uade.tpo.demo.entity.Category;
import com.uade.tpo.demo.entity.Product;
import com.uade.tpo.demo.exceptions.InsufficientStockException;
import com.uade.tpo.demo.exceptions.InvalidPriceException;
import com.uade.tpo.demo.exceptions.InvalidProductDataException;
import com.uade.tpo.demo.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {


    @Autowired
    private ProductRepository productRepository;

    @Override
    public Optional<Product> getProductById(Long productId) {
        return productRepository.findById(productId);
    }

    @Override
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }

    @Override
    public Page<Product> getProducts(PageRequest pageRequest) {
        return productRepository.findAll(pageRequest);
    }

    @Override
    public Product  updateProduct(Long productId, String name, String description,String genre, Double price, Integer stock) throws InvalidPriceException, InsufficientStockException {
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

    @Override
    public Product createProduct(String description, String model, String genre, String image, Double price, Integer stock, Category category)
            throws InvalidProductDataException, InvalidPriceException, InsufficientStockException {
                if (description == null || description.isEmpty()) {
                    throw new InvalidProductDataException();
                }
                if (price <= 10000) {
                    throw new InvalidPriceException();
                }
                if (stock < 0) {
                    throw new InsufficientStockException();
                }
                Product product = Product.builder()
                        .description(description)
                        .model(model)
                        .genre(genre)
                        .price(price)
                        .stock(stock)
                        .category(category)
                        .build();
                return productRepository.save(product);
            }
    }    
