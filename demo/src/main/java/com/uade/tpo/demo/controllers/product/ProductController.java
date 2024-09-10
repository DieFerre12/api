package com.uade.tpo.demo.controllers.product;

import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.uade.tpo.demo.entity.Category.CategoryType;
import com.uade.tpo.demo.entity.Product;
import com.uade.tpo.demo.entity.Size;
import com.uade.tpo.demo.exceptions.InsufficientStockException;
import com.uade.tpo.demo.exceptions.InvalidPriceException;
import com.uade.tpo.demo.exceptions.InvalidProductDataException;
import com.uade.tpo.demo.repository.ProductRepository;
import com.uade.tpo.demo.service.product.ProductService;

import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public ResponseEntity<Page<Product>> getProducts(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        if (page == null || size == null)
            return ResponseEntity.ok(productService.getProducts(PageRequest.of(0, Integer.MAX_VALUE)));
        return ResponseEntity.ok(productService.getProducts(PageRequest.of(page, size)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @DeleteMapping("/{model}")
    public ResponseEntity<String> deleteProduct(@PathVariable String model) {
        try {
            productService.deleteProduct(model);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.ok("Product deleted successfully.");
        }
    }


    @PostMapping("/new")
    public ResponseEntity<Object> createProduct(@RequestBody ProductRequest productRequest)
            throws InvalidProductDataException, InvalidPriceException, InsufficientStockException {
        Product result = productService.createProduct(
                productRequest.getDescription(),
                productRequest.getModel(),
                productRequest.getGenre(),
                productRequest.getImage(),
                productRequest.getPrice(),
                productRequest.getStock(),
                productRequest.getCategoryType(),
                productRequest.getBrand(),
                productRequest.getSize());
        return ResponseEntity.created(URI.create("/products/" + result.getId())).body(result);
    }
 
    @PutMapping("/{model}/{size}") // CAMBIA STOCK DE CADA TALLE
    public ResponseEntity<Product> updateProductSize(@PathVariable String model, @PathVariable Size size, @RequestBody ProductRequest productRequest) {
        try {
            Product updatedProductSize = productService.updateProductSize(
                    model,
                    size,
                    productRequest.getStock()
            );
            return ResponseEntity.ok(updatedProductSize);
        } catch (InsufficientStockException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }



    @PutMapping("/{model}") // CAMBIA PRECIO DE PRODUCTO
public ResponseEntity<?> updateProductPrice(@PathVariable String model, @RequestBody ProductRequest productRequest) {
    try {
        if (productRequest.getPrice() <= 0) {
            throw new InvalidPriceException("El precio es inválido");
        }

        List<Product> products = productRepository.findByModel(model);
        if (products.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado");
        }

        products.forEach(product -> {
            product.setPrice(productRequest.getPrice());
            productRepository.save(product);
        });

        return ResponseEntity.ok(products.get(0)); 

    } catch (InvalidPriceException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}



    @GetMapping("/category/{categoryType}")
    public ResponseEntity<List<Product>> getProductsByCategoryType(@PathVariable CategoryType categoryType) {
        List<Product> products = productService.findByCategoryType(categoryType);
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(products);
    }


}
