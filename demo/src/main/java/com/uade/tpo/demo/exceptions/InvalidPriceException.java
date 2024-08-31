package com.uade.tpo.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//l precio de un artículo es inválido, por ejemplo, cuando es negativo o cero.

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "El precio del artículo es inválido")
public class InvalidPriceException extends Exception {
}
