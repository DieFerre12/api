package com.uade.tpo.demo.controllers.order;

import com.uade.tpo.demo.entity.Order;
import com.uade.tpo.demo.service.order.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order, @RequestParam double baseAmount) {
        Order newOrder = orderService.createOrder(order, baseAmount);
        return ResponseEntity.ok(newOrder);
    }
}
