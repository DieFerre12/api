package com.uade.tpo.demo.controllers.order;

import java.util.Date;

import com.uade.tpo.demo.entity.User;

import lombok.Data;

@Data
public class OrderRequest {
    private User user;
    private Date orderDate;
    private String paymentMethod;
}
