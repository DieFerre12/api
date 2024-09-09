package com.uade.tpo.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//artículo que ya existe en el inventario

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "El artículo ya existe")
public class ItemDuplicateException extends RuntimeException {
    public ItemDuplicateException(String message) {
        super(message);
    }
}

