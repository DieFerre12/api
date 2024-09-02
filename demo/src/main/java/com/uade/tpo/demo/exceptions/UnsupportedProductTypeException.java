package com.uade.tpo.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//tipo de artículo específico que no es soportado 
//por ejemplo, intentar agregar ropa en lugar de zapatillas

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "El tipo de artículo no es soportado por el sistema")
public class UnsupportedProductTypeException extends Exception {
}
