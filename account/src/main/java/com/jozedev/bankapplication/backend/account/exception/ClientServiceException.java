package com.jozedev.bankapplication.backend.account.exception;

public class ClientServiceException extends RuntimeException {

    public ClientServiceException(String message, Object... args) {
        super((args != null && args.length > 0) ? String.format(message, args) : message);
    }
}
