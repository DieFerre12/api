package com.uade.tpo.demo.service.order;

//package com.uade.tpo.demo.service.impl;

import com.uade.tpo.demo.entity.Order;
import com.uade.tpo.demo.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Order createOrder(Order order, String payment, Date  orderDate) {
        return orderRepository.save(order);
    }

    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }
}

