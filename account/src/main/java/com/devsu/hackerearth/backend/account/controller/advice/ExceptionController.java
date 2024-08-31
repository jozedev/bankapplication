package com.devsu.hackerearth.backend.account.controller.advice;

import com.devsu.hackerearth.backend.account.exception.AccountNotActiveException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.devsu.hackerearth.backend.account.exception.NotAvailableBalanceException;

import javax.persistence.EntityNotFoundException;

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

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> catchEntityNotFoundException(EntityNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Couldn't find the requested resource");
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<String> catchEmptyResultDataAccessException(EmptyResultDataAccessException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Couldn't find the requested resource");
    }
}
