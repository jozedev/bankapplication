package com.jozedev.bankapplication.backend.account.controller.advice;

import com.jozedev.bankapplication.backend.account.exception.AccountNotActiveException;
import com.jozedev.bankapplication.backend.account.exception.ClientServiceException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.jozedev.bankapplication.backend.account.exception.NotAvailableBalanceException;

import jakarta.persistence.EntityNotFoundException;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(NotAvailableBalanceException.class)
    public ResponseEntity<String> catchNotAvailableBalanceException(NotAvailableBalanceException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Saldo no disponible");
    }

    @ExceptionHandler(AccountNotActiveException.class)
    public ResponseEntity<String> catchAccountNotEnabledException(AccountNotActiveException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La cuenta no esta activa");
    }

    @ExceptionHandler(ClientServiceException.class)
    public ResponseEntity<String> catchClientServiceException(ClientServiceException exception) {
        return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body(exception.getMessage());
    }

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
