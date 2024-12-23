package com.uade.tpo.demo.controllers.product;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uade.tpo.demo.entity.Brand;
import com.uade.tpo.demo.entity.Category.CategoryType;
import com.uade.tpo.demo.entity.Product;
import com.uade.tpo.demo.entity.Size;
import com.uade.tpo.demo.exceptions.InsufficientStockException;
import com.uade.tpo.demo.exceptions.InvalidPriceException;
import com.uade.tpo.demo.exceptions.InvalidProductDataException;
import com.uade.tpo.demo.repository.ProductRepository;
import com.uade.tpo.demo.service.product.ProductService;

@CrossOrigin(origins = "http://localhost:5173")
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

    @GetMapping("/{model}")
    public ResponseEntity<List<Product>> getProductsByModel(@PathVariable String model) {
        List<Product> products = productService.getProductByModelWithImage(model);

        if (products.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(products);
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

    System.out.println("Recibido productRequest: " + productRequest); // Línea de depuración

    List<Product> result = productService.createProduct(
            productRequest.getDescription(),
            productRequest.getModel(),
            productRequest.getGenre(),
            productRequest.getPrice(),
            productRequest.getSizeStockMap(),  // Pasar el mapa de tallas y stocks
            productRequest.getCategoryType(),
            productRequest.getBrand());

    return ResponseEntity.created(URI.create("/products/" + result.get(0).getId())).body(result);
}

    @PutMapping("/updateProductPrice")
    public ResponseEntity<Object> updateProductPrice(@RequestBody ProductRequest productRequest) {
        try {
            List<Product> updatedProducts = productService.updateProductPrice(
                    productRequest.getModel(),
                    productRequest.getPrice());

            return ResponseEntity.ok(updatedProducts);
        } catch (InvalidPriceException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

        
    @PutMapping("/updateProductSize")
    public ResponseEntity<List<Product>> updateProductSize(@RequestBody ProductRequest productRequest) {
        List<Product> updatedProducts = new ArrayList<>();
        try {
            for (Map.Entry<Size, Integer> entry : productRequest.getSizeStockMap().entrySet()) {
                Size size = entry.getKey();
                Integer stock = entry.getValue();
                
                // Llama al método updateProductSize para cada talla
                Product updatedProductSize = productService.updateProductSize(
                        productRequest.getModel(),
                        size,
                        stock
                );
                updatedProducts.add(updatedProductSize);
            }
            return ResponseEntity.ok(updatedProducts);
        } catch (InsufficientStockException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
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

    @GetMapping("/brand/{brand}")
    public ResponseEntity<List<Product>> getProductsByBrand(@PathVariable Brand brand) {
        List<Product> products = productService.findByBrand(brand);
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(products);
    }

}
