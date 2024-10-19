package com.uade.tpo.demo.controllers.shoppingCart;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.uade.tpo.demo.controllers.user.UserResponse;
import com.uade.tpo.demo.entity.Product;
import com.uade.tpo.demo.entity.ShoppingCart;
import com.uade.tpo.demo.entity.Size;
import com.uade.tpo.demo.service.product.ProductService;
import com.uade.tpo.demo.service.shoppingCart.ShoppingCartService;

@CrossOrigin(origins = "http://localhost:5173") 
@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private ProductService productService;

    
    @GetMapping("/getAll")
    public List<ShoppingCart> getAllCarts() {
        return shoppingCartService.getAllCarts();
    }

    @GetMapping("/user/{userId}")
public ResponseEntity<?> getCartByUserId(@PathVariable Long userId) {
    Optional<ShoppingCart> cart = shoppingCartService.getCartByUserId(userId);

    if (cart.isPresent()) {
        ShoppingCart shoppingCart = cart.get();

        UserResponse userResponse = new UserResponse(userId, shoppingCart.getUser().getEmail(), shoppingCart.getUser().getFirstName(), shoppingCart.getUser().getLastName());

        ShoppingCartRequest shoppingCartRequest = ShoppingCartRequest.builder()
            .id(shoppingCart.getId())
            .totalPrice(shoppingCart.getTotalPrice())
            .userResponse(userResponse)
            .products(shoppingCart.getItems().stream()
                .map(item -> new ShoppingCartRequest.ProductRequest(
                    item.getSize(),
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
public ResponseEntity<?> addProductToCart(
        @PathVariable Long userId,
        @RequestBody ShoppingCartRequest.ProductRequest productCart) {
    
    // Busca el producto por el modelo
    List<Product> products = productService.getProductByModel(productCart.getModel());
    if (products.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado con modelo: " + productCart.getModel());
    }
    
    Product product = products.get(0);
    
    // Verificar la cantidad
    if (productCart.getQuantity() <= 0) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La cantidad debe ser mayor que cero.");
    }
    
    if (productCart.getQuantity() > product.getStock()) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Cantidad solicitada excede el stock disponible.");
    }
    
    // Agrega el producto al carrito con la cantidad especificada
    ShoppingCart cart = shoppingCartService.addProductToCart(userId, product, productCart.getQuantity(), productCart.getModel(), productCart.getSize());

    // Prepara la respuesta
    ShoppingCartRequest response = new ShoppingCartRequest();
    response.setId(cart.getId());
    response.setTotalPrice(cart.getTotalPrice());
    response.setUserResponse(new UserResponse(userId, cart.getUser().getEmail(), cart.getUser().getFirstName(), cart.getUser().getLastName()));

    List<ShoppingCartRequest.ProductRequest> productResponses = cart.getItems().stream()
    .map(item -> new ShoppingCartRequest.ProductRequest(
        item.getSize(), 
        item.getProduct().getModel(),
        item.getProduct().getPrice(),
        item.getQuantity()
    ))
    .toList();
    response.setProducts(productResponses);

    return ResponseEntity.ok(response);
}

@PutMapping("/user/{userId}/updateProduct")
public ResponseEntity<?> updateProductInCart(
        @PathVariable Long userId,
        @RequestBody ShoppingCartRequest.ProductRequest productCart) {

    // Buscar el producto por el modelo
    List<Product> products = productService.getProductByModel(productCart.getModel());

    // Verificar si el producto existe
    if (products.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado con modelo: " + productCart.getModel());
    }

    Product product = products.get(0);

    // Validar la cantidad
    if (productCart.getQuantity() <= 0) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La cantidad debe ser mayor que cero.");
    }

    if (productCart.getQuantity() > product.getStock()) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Cantidad solicitada excede el stock disponible.");
    }

    // Actualizar el producto en el carrito
    ShoppingCart cart = shoppingCartService.updateProductInCart(userId, product, productCart.getQuantity(), productCart.getSize(), productCart.getModel());

    // Preparar la respuesta
    ShoppingCartRequest response = ShoppingCartRequest.builder()
        .id(cart.getId())
        .totalPrice(cart.getTotalPrice())
        .userResponse(new UserResponse(userId, cart.getUser().getEmail(), cart.getUser().getFirstName(), cart.getUser().getLastName()))
        .products(cart.getItems().stream()
            .map(item -> new ShoppingCartRequest.ProductRequest(
                item.getSize(), 
                item.getProduct().getModel(),
                item.getProduct().getPrice(),
                item.getQuantity()))
            .toList())
        .build();

    return ResponseEntity.ok(response);
}

    @DeleteMapping("/user/{userId}/removeProduct/{model}/{size}")
    public ResponseEntity<?> removeProductFromCart(@PathVariable Long userId, @PathVariable String model, @PathVariable Size size) {
        shoppingCartService.removeProductFromCart(userId, size, model);
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
