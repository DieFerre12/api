package com.uade.tpo.demo.service.order;

import com.uade.tpo.demo.entity.Order;

public interface OrderService {
    Order createOrder(Long id, String paymentMethod, String orderDate);
    Order getOrderById(Long orderId);
    double calculateTotal(double baseAmount, String paymentMethod);
}
