package com.mellow.jpa.service.exception;

public final class UnAuthorizedException extends ServiceException {

    private static final long serialVersionUID = 41534214506311259L;

    public UnAuthorizedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnAuthorizedException(String message) {
        super(message);
    }

    public UnAuthorizedException() {
        super();
    }
}