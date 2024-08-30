package com.devsu.hackerearth.backend.account.controller.advice;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.devsu.hackerearth.backend.account.exception.NotAvailableBalanceException;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(NotAvailableBalanceException.class)
    public ResponseEntity<String> catchNotAvailableBalanceException(NotAvailableBalanceException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Saldo no disponible");
    }
}
