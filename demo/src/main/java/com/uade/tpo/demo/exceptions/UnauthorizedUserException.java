package com.uade.tpo.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//usuario intenta agregar artículos al inventario sin los permisos adecuados

@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "El usuario no está autorizado para realizar esta acción")
public class UnauthorizedUserException extends Exception {
}

