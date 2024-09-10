package com.uade.tpo.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "El tipo de artículo no es soportado")
public class UnsupportedProductTypeException extends RuntimeException {
    public UnsupportedProductTypeException (String message) {
        super(message);
    }
}