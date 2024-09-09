package com.uade.tpo.demo.service.product;

import java.util.Optional;
import java.sql.Blob;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.uade.tpo.demo.entity.Category.CategoryType;
import com.uade.tpo.demo.entity.Product;
import com.uade.tpo.demo.entity.Brand;
import com.uade.tpo.demo.entity.CartItem;
import com.uade.tpo.demo.entity.Category;
import com.uade.tpo.demo.entity.Size;
import com.uade.tpo.demo.exceptions.InsufficientStockException;
import com.uade.tpo.demo.exceptions.InvalidPriceException;
import com.uade.tpo.demo.exceptions.InvalidProductDataException;
import com.uade.tpo.demo.repository.CartItemRepository;
import com.uade.tpo.demo.repository.CategoryRepository;
import com.uade.tpo.demo.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private CategoryRepository  categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Override
    public Optional<Product> getProductById(Long productId) {
        return productRepository.findById(productId);
    }

    public void deleteProduct(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            
            // Eliminar los CartItem asociados
            List<CartItem> cartItems = cartItemRepository.findByProduct(product);
            cartItemRepository.deleteAll(cartItems);
            
            // Eliminar el producto
            productRepository.delete(product);
        } else {
            return;
        }
    }

    @Override
    public Page<Product> getProducts(PageRequest pageRequest) {
        return productRepository.findAll(pageRequest);
    }

    @Override
    public Product updateProduct(Long productId, String name, String description, String genre, Double price, Integer stock) 
            throws InvalidPriceException, InsufficientStockException {

        if (price == null || price <= 0) {
            throw new InvalidPriceException();
        }
        if (stock == null || stock < 0) {
            throw new InsufficientStockException();
        }

        return productRepository.findById(productId).map(product -> {
            product.setDescription(description);
            product.setGenre(genre);
            product.setPrice(price);
            product.setStock(stock);
            return productRepository.save(product);
        }).orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    @Override
public Product createProduct(String description, String model, String genre, Blob image, Double price, Integer stock, 
                             CategoryType categoryType, Brand brand, Size size) 
        throws InvalidProductDataException, InvalidPriceException, InsufficientStockException {

    if (description == null || description.isEmpty()) {
        throw new InvalidProductDataException();
    }
    if (price == null || price <= 0) {
        throw new InvalidPriceException();
    }
    if (stock == null || stock < 0) {
        throw new InsufficientStockException();
    }

    Category category = categoryRepository.findByCategoryType(categoryType)
            .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));

    Product product = Product.builder()
            .description(description)
            .model(model)
            .genre(genre)
            .image(image)
            .price(price)
            .stock(stock)
            .category(category)  
            .brand(brand)
            .size(size)
            .build();

    return productRepository.save(product);
    }

    @Override
public Optional<Product> findByCategoryType(CategoryType categoryType) {
    // Obtén la categoría desde el repositorio usando el CategoryType
    Category category = categoryRepository.findByCategoryType(categoryType)
        .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));
    
    // Busca productos usando la categoría obtenida
    return productRepository.findByCategory(category);
}


}


