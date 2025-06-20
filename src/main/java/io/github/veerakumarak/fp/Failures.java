package io.github.veerakumarak.fp;

import io.github.veerakumarak.fp.failures.IllegalArgument;

import java.util.Objects;

public class Failures {

    public static Failure empty() {
        return Failure.getEmpty();
    }

    public static Failure with(String message) {
        checkMessage(message);
        return new Failure(message);
    }

    private static void checkMessage(String message) {
        if (message == null || message.isBlank()) {
            throw new IllegalArgument("AppError message cannot be null or blank when created directly.");
        }
    }

    public static Failure wrap(String message, Throwable cause) {
        String finalMessage = message;
        if (finalMessage == null || finalMessage.isBlank()) {
            finalMessage = (cause != null && cause.getMessage() != null && !cause.getMessage().isBlank())
                    ? cause.getMessage()
                    : "An unexpected error occurred."; // Default fallback
        }
        return new Failure(finalMessage, cause);
    }

    public static Failure wrap(String message, Failure failure) {
        Objects.requireNonNull(failure, "Original failure to wrap cannot be null.");
        return wrap(message, (Throwable) failure);
    }


}
