package io.github.veerakumarak.fp;

import java.util.Objects;

public class Error {

    final private boolean isError;
    final private String message;

    public Error(String message) {
        Objects.requireNonNull(message);
        this.isError = true;
        this.message = message;
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
        } catch (Throwable var2) {
            Throwable t = var2;
            return new Error(t.getMessage());
        }
    }

}
