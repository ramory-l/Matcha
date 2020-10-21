package ru.school.matcha.exceptions;

public class MatchaException extends RuntimeException {

    public MatchaException() {
        super();
    }

    public MatchaException(String message) {
        super(message);
    }

    public MatchaException(String message, Throwable cause) {
        super(message, cause);
    }

    public MatchaException(Throwable cause) {
        super(cause);
    }

    protected MatchaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
