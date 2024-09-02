package com.uade.tpo.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//artículo que ya existe en el inventario

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "El artículo ya existe en el inventario")
public class ItemDuplicateException extends Exception {

}


