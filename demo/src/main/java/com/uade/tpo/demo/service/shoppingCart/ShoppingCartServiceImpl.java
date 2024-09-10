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

    // Verificar si el tamaño es nulo
    if (size == null) {
        throw new IllegalArgumentException("El tamaño (size) no puede ser nulo.");
    }

    // Verificar si hay suficiente stock del producto específico
    Product existingProduct = productService.getProductByModelAndSize(model, size)
        .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado: " + model + " tamaño: " + size));
              

    if (existingProduct.getStock() < quantity) {
        throw new IllegalArgumentException("No hay suficiente stock para el producto: " + model + " tamaño: " + size);
    }

    // Busca si el producto ya está en el carrito
    CartItem item = cart.getItems().stream()
        .filter(i -> i.getProduct().getId().equals(existingProduct.getId()) && i.getSize() == size)
        .findFirst()
        .orElse(null);

    if (item == null) {
        // Si el producto no está en el carrito, añade uno nuevo con la cantidad especificada
        item = new CartItem();
        item.setProduct(existingProduct);
        item.setQuantity(quantity);
        item.setSize(size);  // Aquí asignamos el size correctamente
        item.setShoppingCart(cart);
        cart.getItems().add(item);
    } else {
        // Si el producto ya está en el carrito, actualiza la cantidad
        item.setQuantity(item.getQuantity() + quantity);
    }

    // Restar la cantidad del stock del producto específico
    existingProduct.setStock(existingProduct.getStock() - quantity);
    productRepository.save(existingProduct);

    // Actualizar el total del carrito
    cart.setTotalPrice(cart.getItems().stream()
        .mapToDouble(i -> i.getProduct().getPrice() * i.getQuantity())
        .sum());

    return shoppingCartRepository.save(cart);
}



@Override
public void removeProductFromCart(Long userId, Size size, String model) {
    // Obtener el carrito del usuario
    ShoppingCart cart = getCartByUserId(userId).orElseThrow(() -> new RuntimeException("Carrito no encontrado"));
    
    // Buscar el CartItem correspondiente en el carrito
    CartItem item = cart.getItems().stream()
        .filter(i -> i.getProduct().getModel().equals(model) && i.getProduct().getSize().equals(size))
        .findFirst()
        .orElseThrow(() -> new RuntimeException("Producto no encontrado en el carrito"));

    // Obtener la cantidad del producto en el carrito
    int quantity = item.getQuantity();

    // Obtener el producto
    Product product = item.getProduct();
    
    // Ajustar el stock del producto
    product.setStock(product.getStock() + quantity);
    
    // Eliminar el producto del carrito
    cart.getItems().remove(item);
    
    // Actualizar el total del carrito
    cart.setTotalPrice(cart.getItems().stream()
        .mapToDouble(i -> i.getProduct().getPrice() * i.getQuantity())
        .sum());
    
    // Guardar los cambios en el carrito y el producto
    productRepository.save(product); // Asegúrate de tener el repositorio de productos disponible para guardar el producto
    shoppingCartRepository.save(cart); // Guardar el carrito con los cambios
}


    @Override
    public void clearCartByUserId(Long userId) {
        ShoppingCart cart = getCartByUserId(userId).orElseThrow(() -> new RuntimeException("Carrito no encontrado"));
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
    // Obtener el carrito del usuario
    ShoppingCart cart = getCartByUserId(userId).orElseThrow(() -> new RuntimeException("Carrito no encontrado"));

    // Buscar el CartItem correspondiente en el carrito
    CartItem item = cart.getItems().stream()
        .filter(i -> i.getProduct().getModel().equals(model) && i.getSize() == size)
        .findFirst()
        .orElseThrow(() -> new RuntimeException("Producto no encontrado en el carrito"));

    // Obtener la cantidad actual del producto en el carrito
    int oldQuantity = item.getQuantity();

    // Actualizar la cantidad del producto en el carrito
    item.setQuantity(newQuantity);

    // Ajustar el stock del producto
    if (newQuantity > oldQuantity) {
        // Si se está incrementando la cantidad, restar del stock
        int quantityToDecrease = newQuantity - oldQuantity;
        if (product.getStock() < quantityToDecrease) {
            throw new IllegalArgumentException("No hay suficiente stock para el producto: " + product.getModel());
        }
        product.setStock(product.getStock() - quantityToDecrease);
    } else if (newQuantity < oldQuantity) {
        // Si se está disminuyendo la cantidad, sumar al stock
        int quantityToIncrease = oldQuantity - newQuantity;
        product.setStock(product.getStock() + quantityToIncrease);
    }

    // Actualizar el total del carrito
    cart.setTotalPrice(cart.getItems().stream()
        .mapToDouble(i -> i.getProduct().getPrice() * i.getQuantity())
        .sum());

    // Guardar los cambios en el carrito y el producto
    // Asegúrate de tener el repositorio de productos disponible para guardar el producto
    productRepository.save(product); // Guardar el producto con el stock actualizado
    return shoppingCartRepository.save(cart); // Guardar el carrito con los cambios
}

}
