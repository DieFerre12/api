package com.uade.tpo.demo.service.order;

//package com.uade.tpo.demo.service.impl;

import com.uade.tpo.demo.entity.Order;
import com.uade.tpo.demo.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    @Transactional
    public Order createOrder(Order order, double baseAmount) {
        // Calculamos el total aplicando descuento o recargo
        double totalAmount = order.calculateTotal(baseAmount);
        
        // Aquí puedes setear el total en la factura u otro campo, si es necesario
        // order.setTotalAmount(totalAmount); // Si tienes un campo para el total

        // Guardamos la orden en la base de datos
        return orderRepository.save(order);
    }
}

