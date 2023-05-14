package org.tinder.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message, Throwable err) {
        super(message, err);
    }

    public NotFoundException(Throwable err) {
        super(err);
    }

    public NotFoundException(String message) {
        super(message);
    }
}
