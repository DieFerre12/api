package com.uade.tpo.demo.service.order;


import com.uade.tpo.demo.entity.Order;

public interface OrderService {

    /**
     * Crea una nueva orden y calcula el total basado en el método de pago.
     *
     * @param order      La orden a crear.
     * @param baseAmount El monto base antes de aplicar descuentos o recargos.
     * @return La orden creada con el total calculado.
     */
    Order createOrder(Order order, double baseAmount);
}


