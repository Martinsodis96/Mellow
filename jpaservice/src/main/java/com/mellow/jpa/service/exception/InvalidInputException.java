package com.mellow.jpa.service.exception;

public final class InvalidInputException extends ServiceException {

    private static final long serialVersionUID = 827362517506311259L;

    public InvalidInputException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidInputException(String message) {
        super(message);
    }

    public InvalidInputException() {
        super();
    }
}