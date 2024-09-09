package com.uade.tpo.demo.controllers.product;

import java.net.URI;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import javax.sql.rowset.serial.SerialBlob;

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
import org.springframework.web.multipart.MultipartFile;

import com.uade.tpo.demo.entity.Category.CategoryType;
import com.uade.tpo.demo.entity.Product;
import com.uade.tpo.demo.exceptions.InsufficientStockException;
import com.uade.tpo.demo.exceptions.InvalidPriceException;
import com.uade.tpo.demo.exceptions.InvalidProductDataException;
import com.uade.tpo.demo.repository.ProductRepository;
import com.uade.tpo.demo.service.product.ProductService;

import io.jsonwebtoken.io.IOException;

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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
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
                productRequest.getCategoryType(), // Pasar el CategoryType
                productRequest.getBrand(),
                productRequest.getSize());
        return ResponseEntity.created(URI.create("/products/" + result.getId())).body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody ProductRequest productRequest) {
        try {
            Product updatedProduct = productService.updateProduct(
                    id,
                    productRequest.getDescription(),
                    productRequest.getModel(),
                    productRequest.getGenre(),
                    productRequest.getPrice(),
                    productRequest.getStock());
            return ResponseEntity.ok(updatedProduct);
        } catch (InvalidPriceException | InsufficientStockException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/category/{categoryType}")
    public ResponseEntity<Optional<Product>> getProductsByCategoryType(@PathVariable CategoryType categoryType) {
        Optional<Product> products = productService.findByCategoryType(categoryType);
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(products);
    }

    // Endpoint para subir una imagen
    @PostMapping("/add")
    public String agregarProducto(@RequestParam("nombre") String model, @RequestParam("image") MultipartFile image) throws java.io.IOException {
        try {
            // Convertir el archivo a Blob
            SerialBlob imageBlob = new SerialBlob(image.getBytes());

            Product product = new Product();
            product.setModel(model);
            product.setImage(imageBlob);

            productRepository.save(product);
            return "Producto guardado con éxito";
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            return "Error al subir la imagen";
        }
    }

    // Endpoint para obtener la imagen en Base64
    @GetMapping("/imagen/{id}")
    public String obtenerImagen(@PathVariable Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            return "Producto no encontrado";
        }

        try {
            byte[] imagenBytes = product.getImage().getBytes(1, (int) product.getImage().length());
            return Base64.getEncoder().encodeToString(imagenBytes);
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error al recuperar la imagen";
        }
    }

}
