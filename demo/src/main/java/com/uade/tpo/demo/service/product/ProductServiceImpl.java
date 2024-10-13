package com.uade.tpo.demo.service.product;

import java.util.Optional;

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
import com.uade.tpo.demo.entity.Image;
import com.uade.tpo.demo.entity.Size;
import com.uade.tpo.demo.exceptions.InsufficientStockException;
import com.uade.tpo.demo.exceptions.InvalidPriceException;
import com.uade.tpo.demo.exceptions.InvalidProductDataException;
import com.uade.tpo.demo.repository.CartItemRepository;
import com.uade.tpo.demo.repository.CategoryRepository;
import com.uade.tpo.demo.repository.ProductRepository;
import com.uade.tpo.demo.service.imageS.ImageService;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private CategoryRepository  categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ImageService imageService; 

    @Override
    public Optional<Product> getProductById(Long productId) {
        return productRepository.findById(productId);
    }

    public void deleteProduct(String model) {
        List<Product> products = productRepository.findByModel(model);
        if (products.isEmpty()) {
            throw new RuntimeException("Producto no encontrado");
    }

        // Eliminar el producto
        for (Product product : products) {
            List<CartItem> cartItems = cartItemRepository.findByProduct(product);
            cartItemRepository.deleteAll(cartItems);

            productRepository.delete(product);
    }
    }

    @Override
    public Page<Product> getProducts(PageRequest pageRequest) {
        return productRepository.findAll(pageRequest);
    }


    @Override
    public Product createProduct(String description, String model, String genre, Long imageId, Double price, Integer stock, 
                                CategoryType categoryType, Brand brand, Size size) 
            throws InvalidProductDataException, InvalidPriceException, InsufficientStockException {

        if (description == null || description.isEmpty()) {
            throw new InvalidProductDataException("Los datos son inválidos o incompletos");
        }
        if (price == null || price <= 0) {
            throw new InvalidPriceException("Precio invalido");
        }
        if (stock == null || stock < 0) {
            throw new InsufficientStockException();
        }

        Category category = categoryRepository.findByCategoryType(categoryType)
                .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));
        
        Image image = null; // Inicializamos la imagen como null
        if (imageId != null) {  // Si el imageId no es nulo, intentamos buscar la imagen
            try {
                image = imageService.viewById(imageId);
            } catch (RuntimeException e) {
                // Si no se encuentra la imagen, simplemente dejamos la imagen como null
                System.out.println("Imagen no encontrada, se procederá sin imagen.");
            }
        }    

        Product product = Product.builder()
                .description(description)
                .model(model)
                .genre(genre)
                .price(price)
                .stock(stock)
                .category(category)  
                .brand(brand)
                .size(size)
                .image(image)
                .build();

        return productRepository.save(product);
        }

    @Override
    public List<Product> findByCategoryType(CategoryType categoryType) {
        Category category = categoryRepository.findByCategoryType(categoryType)
            .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));
        
        return productRepository.findByCategory(category);
    }

    
    @Override
    public Product updateProductSize(String model, Size size, Integer stock) 
            throws InsufficientStockException {

        if (stock == null || stock < 0) {
            throw new InsufficientStockException();
        }
        // Busca el producto por el modelo y tamaño
        Product product = productRepository.findByModelAndSize(model, size)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        // Actualiza el producto encontrado
        product.setStock(stock);
        return productRepository.save(product);
    }

    @Override
    public List<Product> updateProductPrice(String model, Double price) 
            throws InvalidPriceException {
       if (price == null || price <= 0) {
        throw new InvalidPriceException("Precio invalido");
    }
    List<Product> products = productRepository.findByModel(model);
    if (products.isEmpty()) {
        throw new RuntimeException("Producto no encontrado");
    }

    // Actualizar los productos que tienen ese modelo
    products.forEach(product -> {
        product.setPrice(price);
        productRepository.save(product);
    });

    return products;
            
    }

    @Override
    public List<Product> getProductByModel(String model) {
        List<Product> products = productRepository.findByModel(model);
    if (products.isEmpty()) {
        throw new RuntimeException("Producto no encontrado");
    }
    return products;
    }

    public Optional<Product> getProductByModelAndSize(String model, Size size) {
        return productRepository.findByModelAndSize(model, size);
    }
}


