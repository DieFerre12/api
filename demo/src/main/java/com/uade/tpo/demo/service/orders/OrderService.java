package com.uade.tpo.demo.service.orders;

import com.uade.tpo.demo.entity.Order;
import com.uade.tpo.demo.entity.ShoppingCart;
import com.uade.tpo.demo.exceptions.InsufficientStockException;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    public Order saveOrder(Order order);

    public Optional<Order> getOrderById(Long id);

    public List<Order> getAllOrders();

    public void deleteOrder(Long id);
    
    Order createOrder(Long userId, ShoppingCart cart) throws InsufficientStockException;

}
