package com.jozedev.bankapplication.backend.client.controller.advice;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.persistence.EntityNotFoundException;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> catchEntityNotFoundException(EntityNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se pudo encontrar el recurso solicitado");
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<String> catchEmptyResultDataAccessException(EmptyResultDataAccessException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se pudo encontrar el recurso solicitado");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> catchException(Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrión un error inesperado");
    }
}
