package com.mellow.exception;

public final class InvalidUserException extends RuntimeException {

    private static final long serialVersionUID = -7272435762445274162L;

    public InvalidUserException(String message) {
        super(message);
    }
}