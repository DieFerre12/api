package com.uade.tpo.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//no hay suficiente stock disponible

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Stock insuficiente para el artículo solicitado")
public class InsufficientStockException extends Exception {

}
