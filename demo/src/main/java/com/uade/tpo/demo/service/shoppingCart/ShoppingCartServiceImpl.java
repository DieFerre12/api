package com.uade.tpo.demo.service.shoppingCart;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uade.tpo.demo.entity.CartItem;
import com.uade.tpo.demo.entity.Product;
import com.uade.tpo.demo.entity.ShoppingCart;
import com.uade.tpo.demo.entity.Size;
import com.uade.tpo.demo.repository.ShoppingCartRepository;
import com.uade.tpo.demo.repository.ProductRepository;
import com.uade.tpo.demo.repository.UserRepository;
import com.uade.tpo.demo.service.product.ProductService;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired 
    private    ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<ShoppingCart> getAllCarts() {
        return shoppingCartRepository.findAll();
    }

    @Override
    public Optional<ShoppingCart> getCartByUserId(Long userId) {
        return shoppingCartRepository.findByUserId(userId);
    }

    @Override
public ShoppingCart addProductToCart(Long userId, Product product, int quantity, String model, Size size) {
    ShoppingCart cart = getCartByUserId(userId).orElseGet(() -> createCart(userId));

    if (size == null) {
        throw new IllegalArgumentException("El tamaño (size) no puede ser nulo.");
    }

    Product existingProduct = productService.getProductByModelAndSize(model, size)
        .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado: " + model + " tamaño: " + size));
              

    if (existingProduct.getStock() < quantity) {
        throw new IllegalArgumentException("No hay suficiente stock para el producto: " + model + " tamaño: " + size);
    }

    CartItem item = cart.getItems().stream()
        .filter(i -> i.getProduct().getId().equals(existingProduct.getId()) && i.getSize() == size)
        .findFirst()
        .orElse(null);

    if (item == null) {
        item = new CartItem();
        item.setProduct(existingProduct);
        item.setQuantity(quantity);
        item.setSize(size);  
        item.setShoppingCart(cart);
        cart.getItems().add(item);
    } else {
        item.setQuantity(item.getQuantity() + quantity);
    }

    existingProduct.setStock(existingProduct.getStock() - quantity);
    productRepository.save(existingProduct);

    cart.setTotalPrice(cart.getItems().stream()
        .mapToDouble(i -> i.getProduct().getPrice() * i.getQuantity())
        .sum());

    return shoppingCartRepository.save(cart);
}



@Override
public void removeProductFromCart(Long userId, Size size, String model) {
    ShoppingCart cart = getCartByUserId(userId).orElseThrow(() -> new RuntimeException("Carrito no encontrado"));
    
    CartItem item = cart.getItems().stream()
        .filter(i -> i.getProduct().getModel().equals(model) && i.getProduct().getSize().equals(size))
        .findFirst()
        .orElseThrow(() -> new RuntimeException("Producto no encontrado en el carrito"));

    int quantity = item.getQuantity();

    Product product = item.getProduct();
    
    product.setStock(product.getStock() + quantity);
    
    cart.getItems().remove(item);
    
    cart.setTotalPrice(cart.getItems().stream()
        .mapToDouble(i -> i.getProduct().getPrice() * i.getQuantity())
        .sum());
    
    productRepository.save(product); 
    shoppingCartRepository.save(cart); 
}


    @Override
    public void clearCartByUserId(Long userId) {
        ShoppingCart cart = getCartByUserId(userId).orElseThrow(() -> new RuntimeException("Carrito no encontrado"));

        cart.getItems().forEach(item -> {
            Product product = item.getProduct();
            int quantityInCart = item.getQuantity();
            
            product.setStock(product.getStock() + quantityInCart);
            
            productRepository.save(product);
        });
    
        cart.getItems().clear();
        
        shoppingCartRepository.save(cart);
    }

    @Override
    public double calculateTotalPrice(Long userId) {
        ShoppingCart cart = getCartByUserId(userId).orElseThrow(() -> new RuntimeException("Carrito no encontrado"));
        return cart.getTotalPrice();
    }

    @Override
    public ShoppingCart createCart(Long userId) {
        ShoppingCart cart = new ShoppingCart();
        cart.setUser(userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Usuario no encontrado")));
        return shoppingCartRepository.save(cart);
    }

    @Override
public ShoppingCart updateProductInCart(Long userId, Product product, int newQuantity, Size size, String model) {
    ShoppingCart cart = getCartByUserId(userId).orElseThrow(() -> new RuntimeException("Carrito no encontrado"));

    CartItem item = cart.getItems().stream()
        .filter(i -> i.getProduct().getModel().equals(model) && i.getSize() == size)
        .findFirst()
        .orElseThrow(() -> new RuntimeException("Producto no encontrado en el carrito"));

    int oldQuantity = item.getQuantity();

    item.setQuantity(newQuantity);

    if (newQuantity > oldQuantity) {
        int quantityToDecrease = newQuantity - oldQuantity;
        if (product.getStock() < quantityToDecrease) {
            throw new IllegalArgumentException("No hay suficiente stock para el producto: " + product.getModel());
        }
        product.setStock(product.getStock() - quantityToDecrease);
    } else if (newQuantity < oldQuantity) {
        int quantityToIncrease = oldQuantity - newQuantity;
        product.setStock(product.getStock() + quantityToIncrease);
    }

    cart.setTotalPrice(cart.getItems().stream()
        .mapToDouble(i -> i.getProduct().getPrice() * i.getQuantity())
        .sum());

    
    productRepository.save(product); 
    return shoppingCartRepository.save(cart);
}

    @Override
    public void updateProductStock(Product product) {
        productRepository.save(product);
    }

    

}
