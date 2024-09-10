package com.uade.tpo.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//error durante el procesamiento del pago al intentar agregar un artículo al carrito

@ResponseStatus(code = HttpStatus.PAYMENT_REQUIRED, reason = "Error en el proceso de pago")
public class PaymentProcessingException extends RuntimeException {
    public PaymentProcessingException(String message) {
        super(message);
    }
}