package com.jozedev.bankapplication.backend.account.exception;

public class NotAvailableBalanceException extends RuntimeException {

    public NotAvailableBalanceException(String message, Object... args) {
        super((args != null && args.length > 0) ? String.format(message, args) : message);
    }
}
