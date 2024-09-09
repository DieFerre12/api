package com.uade.tpo.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//l precio de un artículo es inválido, por ejemplo, cuando es negativo o cero.

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "El precio es inválido")
public class InvalidPriceException extends RuntimeException {
    public InvalidPriceException(String message) {
        super(message);
    }
}
