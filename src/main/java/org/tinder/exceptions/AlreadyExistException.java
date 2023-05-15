package org.tinder.exceptions;

public class AlreadyExistException extends RuntimeException {
    public AlreadyExistException(String message, Throwable err) {
        super(message, err);
    }

    public AlreadyExistException(String message) {
        super(message);
    }

    public AlreadyExistException(Throwable ex) {
        super(ex);
    }
}
