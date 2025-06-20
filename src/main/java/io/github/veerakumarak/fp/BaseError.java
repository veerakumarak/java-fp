package io.github.veerakumarak.fp;

import java.util.Objects;

public abstract class BaseError extends RuntimeException {

    private final String message;
    private final Throwable cause; // Can be another MyError or any other Throwable

    public BaseError(String message) {
        this.message = message;
        this.cause = null;
    }

    BaseError(String message, Throwable throwable) {
        this.message = message;
        this.cause = throwable;
    }

    public String getMessage() {
        if (Objects.nonNull(message)) {
            return message;
        }
        if (Objects.nonNull(cause)) {
            return cause.getMessage();
        }
        throw Error.with("error is empty");
    }

    public Throwable getCause() {
        return this.cause;
    }

    public Error wrap(Error error) {
        return new Error(message, error);
    }

    public Error unwrap() {
        if (cause instanceof Error) {
            return (Error) cause;
        }
        return new Error(null, null);
    }

    public void orThrow() {
        if (isPresent()) {
            throw this;
        }
    }

    public boolean isPresent() {
        return Objects.nonNull(message) && Objects.nonNull(cause);
    }

    public boolean isEmpty() {
        return !isPresent();
    }

    public boolean isEq(Class<? extends BaseError> errorClass) {
        Objects.requireNonNull(errorClass, "class provided is null");
        return isPresent() && errorClass.isInstance(this);
    }

}
