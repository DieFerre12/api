package com.uade.tpo.demo.service.order;

import com.uade.tpo.demo.controllers.order.OrderResponse;
import com.uade.tpo.demo.entity.Order;

public interface OrderService {
    OrderResponse createOrder(Long id, String paymentMethod, String orderDate);
    Order getOrderById(Long orderId);
    double calculateTotal(double baseAmount, String paymentMethod);
}
