package com.uade.tpo.demo.controllers.shoppingCart;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uade.tpo.demo.controllers.user.UserResponse;
import com.uade.tpo.demo.entity.Product;
import com.uade.tpo.demo.entity.ShoppingCart;
import com.uade.tpo.demo.service.product.ProductService;
import com.uade.tpo.demo.service.shoppingCart.ShoppingCartService;

@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private ProductService productService;

    
    @GetMapping
    public List<ShoppingCart> getAllCarts() {
        return shoppingCartService.getAllCarts();
    }

    @GetMapping("/user/{userId}")
public ResponseEntity<?> getCartByUserId(@PathVariable Long userId) {
    Optional<ShoppingCart> cart = shoppingCartService.getCartByUserId(userId);

    if (cart.isPresent()) {
        ShoppingCart shoppingCart = cart.get();

        UserResponse userResponse = new UserResponse(shoppingCart.getUser().getEmail(), shoppingCart.getUser().getFirstName(), shoppingCart.getUser().getLastName());

        ShoppingCartRequest shoppingCartRequest = ShoppingCartRequest.builder()
            .id(shoppingCart.getId())
            .totalPrice(shoppingCart.getTotalPrice())
            .userResponse(userResponse)
            .products(shoppingCart.getItems().stream()
                .map(item -> new ShoppingCartRequest.ProductRequest(
                    item.getProduct().getId(),
                    item.getProduct().getModel(),
                    item.getProduct().getPrice(),
                    item.getQuantity()
                ))
                .toList()
            )
            .build();

        return ResponseEntity.ok(shoppingCartRequest);
    } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Carrito no encontrado para usuario ID: " + userId);
    }
}


    @PostMapping("/user/{userId}/addProduct")
    public ResponseEntity<?> addProductToCart(@PathVariable Long userId, @RequestBody ShoppingCart.ProductsCart productCart) {
        // Busca el producto
        Optional<Product> productOptional = productService.getProductById(productCart.getId());
        if (!productOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado por ID: " + productCart.getId());
        }

        // Agrega el producto al carrito con la cantidad especificada
        ShoppingCart cart = shoppingCartService.addProductToCart(userId, productOptional.get(), productCart.getQuantity());

        // Prepara la respuesta
        ShoppingCartRequest response = new ShoppingCartRequest();
        response.setId(cart.getId());
        response.setTotalPrice(cart.getTotalPrice());
        response.setUserResponse(new UserResponse(cart.getUser().getEmail(), cart.getUser().getFirstName(), cart.getUser().getLastName()));
        

        List<ShoppingCartRequest.ProductRequest> productResponses = cart.getItems().stream()
            .map(item -> new ShoppingCartRequest.ProductRequest(
                item.getProduct().getId(),
                item.getProduct().getModel(),
                item.getProduct().getPrice(),
                item.getQuantity()  // Devuelve la cantidad correcta en la respuesta
            ))
            .toList();

        response.setProducts(productResponses);

        return ResponseEntity.ok(response);
    }


    @PutMapping("/user/{userId}/updateProduct")
    public ResponseEntity<?> updateProductInCart(@PathVariable Long userId, @RequestBody ShoppingCart.ProductsCart productCart) {
        Optional<Product> productOptional = productService.getProductById(productCart.getId());
        if (!productOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado con ID: " + productCart.getId());
        }

        ShoppingCart cart = shoppingCartService.updateProductInCart(userId, productOptional.get());
        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("/user/{userId}/removeProduct/{productId}")
    public ResponseEntity<?> removeProductFromCart(@PathVariable Long userId, @PathVariable Long productId) {
        shoppingCartService.removeProductFromCart(userId, productId);
        return ResponseEntity.ok("Producto eliminado del carrito.");
    }

    @DeleteMapping("/user/{userId}/clearCart")
    public ResponseEntity<?> clearCartByUserId(@PathVariable Long userId) {
        shoppingCartService.clearCartByUserId(userId);
        return ResponseEntity.ok("Carrito vacio para usuario ID: " + userId);
    }

    @GetMapping("/user/{userId}/totalPrice")
    public ResponseEntity<Double> calculateTotalPrice(@PathVariable Long userId) {
        double totalPrice = shoppingCartService.calculateTotalPrice(userId);
        return ResponseEntity.ok(totalPrice);
    }

    @PostMapping("/user/{userId}/createCart")
    public ResponseEntity<?> createCart(@PathVariable Long userId) {
        ShoppingCart cart = shoppingCartService.createCart(userId);
        return ResponseEntity.ok(cart);
    }
}
