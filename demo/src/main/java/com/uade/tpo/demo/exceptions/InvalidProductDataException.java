package com.uade.tpo.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//datos proporcionados para el artículo son incompletos o incorrectos 
//por ejemplo, falta la talla o la marca

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Los datos del artículo son inválidos o incompletos")
public class InvalidProductDataException extends Exception {


}
