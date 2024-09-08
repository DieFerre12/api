package com.uade.tpo.demo.controllers.order;

import com.uade.tpo.demo.entity.Order;
import com.uade.tpo.demo.service.order.OrderService;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Date;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderRequest orderRequest) {
        try {
            Order order = new Order();
            order.setOrderDate(orderRequest.getOrderDate());
            order.setPaymentMethod(orderRequest.getPaymentMethod());
            order.setUser(orderRequest.getUser()); // Assuming User is already set or retrieved
            // Set other fields if needed

            Order createdOrder = orderService.createOrder(order, orderRequest.getPaymentMethod(), orderRequest.getOrderDate());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al crear la orden: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
public ResponseEntity<?> getOrderById(@PathVariable Long id) {
    // Buscar la orden por ID
    Order order = orderService.getOrderById(id);
    
    // Verificar si se encontró la orden
    if (order != null) {
        return ResponseEntity.ok(order); // Devolver la orden con estado 200 OK
    } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Orden no encontrada con ID: " + id); // Devolver un mensaje de error con estado 404 Not Found
    }
}

}
