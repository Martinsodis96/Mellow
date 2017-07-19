package com.mellow.application.jpaservice.service.exception;

public abstract class ServiceException extends RuntimeException {

    private static final long serialVersionUID = 1529570775397439370L;

    protected ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    protected ServiceException(String message) {
        super(message);
    }

    protected ServiceException() {
        super();
    }
}