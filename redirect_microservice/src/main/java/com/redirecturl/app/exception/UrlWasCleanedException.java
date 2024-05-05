package com.redirecturl.app.exception;

 public class UrlWasCleanedException extends RuntimeException {

    public UrlWasCleanedException() {
        super("URL nie istnieje lub wygasł");
    }

    public UrlWasCleanedException(String message) {
        super(message);
    }
}
