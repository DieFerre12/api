package com.uade.tpo.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "Usuario no autorizado para realizar esta acción")
public class UnauthorizedUserException extends RuntimeException {
    public UnauthorizedUserException (String message) {
        super(message);
    }
}