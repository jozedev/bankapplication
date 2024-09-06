package com.jozedev.bankapplication.backend.account.exception;

public class AccountNotActiveException extends RuntimeException {

    public AccountNotActiveException(String message, Object... args) {
        super((args != null && args.length > 0) ? String.format(message, args) : message);
    }
}
