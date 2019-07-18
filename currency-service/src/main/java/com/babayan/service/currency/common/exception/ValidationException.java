package com.babayan.service.currency.common.exception;

/**
 * @author by artbabayan
 */
public class ValidationException extends IllegalArgumentException {
    public ValidationException() {
        super();
    }

    public ValidationException(String s) {
        super(s);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidationException(Throwable cause) {
        super(cause);
    }

}
