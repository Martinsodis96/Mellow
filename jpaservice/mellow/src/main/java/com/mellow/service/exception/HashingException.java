package com.mellow.service.exception;

public final class HashingException extends ServiceException {

    private static final long serialVersionUID = 1172918374606311252L;

    public HashingException(String message, Exception e) {
        super(message, e);
    }

}
