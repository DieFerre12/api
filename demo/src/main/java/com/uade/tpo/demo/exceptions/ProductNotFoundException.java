package com.uade.tpo.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//el artículo que intentas agregar no existe en el inventario.

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "No se encontró el articulo")
public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String message) {
        super(message);
    }
}