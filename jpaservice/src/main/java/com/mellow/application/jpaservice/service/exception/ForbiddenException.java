package com.mellow.application.jpaservice.service.exception;

public final class ForbiddenException extends ServiceException {

    private static final long serialVersionUID = 7263826183627223534L;

    public ForbiddenException(String message) {
        super(message);
    }

    public ForbiddenException(String message, Exception e) {
        super(message, e);
    }

}
