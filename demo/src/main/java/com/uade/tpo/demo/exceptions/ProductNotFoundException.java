package com.uade.tpo.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//el artículo que intentas agregar no existe en el inventario.

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "El artículo no se encontró en el inventario")
public class ProductNotFoundException extends Exception {
}
