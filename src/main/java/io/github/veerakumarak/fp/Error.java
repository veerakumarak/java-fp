package io.github.veerakumarak.fp;

import java.util.Objects;

public class Error extends BaseError {

    Error(String message, Throwable cause) {
        super(message, cause);
    }

    public static Error empty() {
        return new Error(null, null);
    }

    public static Error with(String message) {
        return new Error(message, null);
    }

    public static Error wrap(String message, Error error) {
        return new Error(message, error);
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
