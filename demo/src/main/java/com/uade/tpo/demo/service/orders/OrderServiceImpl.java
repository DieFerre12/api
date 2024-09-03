package com.uade.tpo.demo.service.orders;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import com.uade.tpo.demo.entity.Detail;
import com.uade.tpo.demo.entity.Order;
import java.time.LocalDateTime;
import com.uade.tpo.demo.entity.Product;
import com.uade.tpo.demo.entity.ShoppingCart;
import com.uade.tpo.demo.exceptions.InsufficientStockException;
import com.uade.tpo.demo.repository.OrderRepository;
import com.uade.tpo.demo.service.shoppingCart.ShoppingCartService;

@Service
public class OrderServiceImpl  implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired ShoppingCartService shoppingCartService;

    @Override
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public void deleteOrder(Long id) {
       orderRepository.deleteById(id);
    }


    @Override
    public Order createOrder(Long userId, ShoppingCart cart) throws InsufficientStockException {
    // Validar que todos los productos en el carrito tengan suficiente stock
    for (Product product : cart.getProducts()) {
        if (product.getStock() < 1) {
            throw new InsufficientStockException();
        }
    }

    List<Detail> details = cart.getProducts().stream()
            .map(product -> Detail.builder()
                    .product(product)
                    .quantity(1) 
                    .build())
            .collect(Collectors.toList());

    
    Order order = Order.builder()
            .user(cart.getUser())
            .facture(null) 
            .details(details)
            .orderDate(LocalDateTime.now().toString()) 
            .build();

    
    for (Detail detail : details) {
        detail.setOrder(order);
    }
    
    Order savedOrder = orderRepository.save(order);

   
    shoppingCartService.clearCartByUserId(userId);

    return savedOrder;
    }

    @Override
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }
}