package com.uade.tpo.demo.service.order;

import java.util.Date;
import com.uade.tpo.demo.entity.Order;

public interface OrderService {
    public Order createOrder(Order order, String payment, Date  orderDate);
    public Order getOrderById(Long id);
}


