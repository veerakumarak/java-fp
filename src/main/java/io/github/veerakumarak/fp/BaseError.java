package io.github.veerakumarak.fp;

import java.util.Objects;

public class BaseError extends RuntimeException {

    private final String message;
    private final Throwable cause; // Can be another MyError or any other Throwable

    public BaseError(String message) {
        this.message = message;
        this.cause = null;
    }

    private BaseError(String message, Throwable throwable) {
        this.message = message;
        this.cause = throwable;
    }

    public static BaseError empty() {
        return new BaseError(null, null);
    }

    public static BaseError with(String message) {
        return new Error(message);
    }

    public static BaseError wrap(String message, Error error) {
        return new BaseError(message, error);
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

    public Error unwrap() {
        if (cause instanceof Error) {
            return (Error) cause;
        }
        return empty();
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

    public boolean isEq(Class<? extends Error> errorClass) {
        Objects.requireNonNull(errorClass, "class provided is null");
        return isPresent() && errorClass.isInstance(this);
    }

    public static Error of(Runnable runnable) {
        Objects.requireNonNull(runnable, "runnable is null");
        try {
            runnable.run();
            return null;
        } catch (Throwable throwable) {
            return Error.with(throwable.getMessage());
        }
    }

}
