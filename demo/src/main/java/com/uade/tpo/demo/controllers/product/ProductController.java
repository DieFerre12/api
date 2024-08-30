package com.uade.tpo.demo.controllers.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uade.tpo.demo.entity.Product;
import com.uade.tpo.demo.service.product.ProductService;

@RestController
@RequestMapping("products")
public class ProductController {
    
    @Autowired ProductService productService;

    @GetMapping public ResponseEntity<Page<Product>> getProducts(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        if (page == null || size == null)
            return ResponseEntity.ok(productService.getProducts(PageRequest.of(0, Integer.MAX_VALUE)));
        return ResponseEntity.ok(productService.getProducts(PageRequest.of(page, size)));
    }


    @GetMapping public ResponseEntity<Product> getProductById(@RequestParam Long productId) {
        return productService.getProductById(productId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping public ResponseEntity<Product> saveProduct(
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam String price,
            @RequestParam String stock) {
        return ResponseEntity.ok(productService.saveProduct(name, description, price, stock));
    }

    @PostMapping public ResponseEntity<Product> updateProduct(
            @RequestParam Long productId,
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam String price,
            @RequestParam String stock) {
        return null; } // a revisar por los metodos faltantes.

}
