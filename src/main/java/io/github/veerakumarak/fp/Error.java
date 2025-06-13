package io.github.veerakumarak.fp;

import java.util.Objects;

public class Error {

    final private boolean isError;
    final private String message;

    private Error(boolean isError, String message) {
        this.isError = isError;
        this.message = message;
    }

    public static Error of(String message) {
        Objects.requireNonNull(message);
        return new Error(true, message);
    }

    public static Error empty() {
        return new Error(false, null);
    }

    public String getMessage() {
        return message;
    }

    public boolean isEq(Class<? extends Error> errorClass) {
        Objects.requireNonNull(errorClass, "class provided is null");
        return errorClass.isInstance(this);
    }

    public boolean isPresent() {
        return this.isError;
    }

    public boolean isEmpty() {
        return !this.isError;
    }

    public void unwrap() {
        if (isPresent()) {
            throw new RuntimeException(this.message);
        }
    }

    static Error of(Runnable runnable) {
        Objects.requireNonNull(runnable, "runnable is null");
        try {
            runnable.run();
            return null;
        } catch (Throwable throwable) {
            return Error.of(throwable.getMessage());
        }
    }

}
