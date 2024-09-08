package com.uade.tpo.demo.service.order;

import com.uade.tpo.demo.entity.*;
import com.uade.tpo.demo.repository.OrderRepository;
import com.uade.tpo.demo.service.shoppingCart.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.uade.tpo.demo.service.order.OrderServiceImpl.*;

@Service
public class OrderServiceImpl implements OrderService {

    private static final String CREDIT_CARD = "tarjeta_credito";
    private static final String DEBIT_CARD = "tarjeta_debito";
    private static final String MERCADO_PAGO = "mercado_pago";
    private static final String CASH = "efectivo";

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Override
    public Order createOrder(Long id, String paymentMethod, String orderDate) {
        validatePaymentMethod(paymentMethod);

        ShoppingCart cart = getShoppingCart(id);
        Order order = buildOrder(cart, paymentMethod, orderDate);
        double finalTotal = calculateTotal(cart.getTotalPrice(), paymentMethod);

        order.setTotalPrice(finalTotal);

        order = orderRepository.save(order);
        shoppingCartService.clearCartByUserId(id);

        return order;
    }

    @Override
    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Orden no encontrada para el ID: " + orderId));
    }

    @Override
    public double calculateTotal(double baseAmount, String paymentMethod) {
        double total = baseAmount;
        switch (paymentMethod.toLowerCase()) {
            case CREDIT_CARD -> total += baseAmount * 0.10;
            case DEBIT_CARD, MERCADO_PAGO -> {
            }
            case CASH -> total -= baseAmount * 0.10;
        }
        return total;
    }

    private void validatePaymentMethod(String paymentMethod) {
        if (!List.of(CREDIT_CARD, DEBIT_CARD, MERCADO_PAGO, CASH).contains(paymentMethod.toLowerCase())) {
            throw new IllegalArgumentException("Método de pago no válido");
        }
    }

    private ShoppingCart getShoppingCart(Long id) {
        return shoppingCartService.getCartByUserId(id)
                .orElseThrow(() -> new IllegalArgumentException("Carrito no encontrado para el usuario ID: " + id));
    }
    
    private Order buildOrder(ShoppingCart cart, String paymentMethod, String orderDate) {
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setPaymentMethod(paymentMethod);
        try {
            Timestamp timestamp = new Timestamp(new SimpleDateFormat("yyyy-MM-dd").parse(orderDate).getTime());
            order.setOrderDate(timestamp);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Formato de fecha no válido. Use 'yyyy-MM-dd'.");
        }
        order.setDetails(cart.getItems().stream().map(item -> {
            Detail detail = new Detail();
            detail.setOrder(order);
            detail.setProduct(item.getProduct());
            detail.setQuantity(item.getQuantity());
            detail.setPrice(item.getProduct().getPrice() * item.getQuantity());
            return detail;
        }).toList());
    
        return order;
    }
}