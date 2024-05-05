package com.shortedurl.app.exception;

public class BadWordException extends RuntimeException {

    public BadWordException() {
        super("URL zawiera niedozwolone słowo");
    }

    public BadWordException(String message) {
        super(message);
    }
}
