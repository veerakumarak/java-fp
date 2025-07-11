package io.github.veerakumarak.fp;

import java.util.Objects;
import java.util.function.Consumer;

public class Failure extends RuntimeException {

    private final Throwable cause;

    private static final Failure EMPTY = new Failure("no failure", null);

    protected Failure(String message, Throwable cause) {
        super(message);
        this.cause = cause;
    }

    protected Failure(String message) {
        this(message, null);
    }

    static Failure getEmpty() {
        return EMPTY;
    }

    @Override
    public final String getMessage() {
        if (this == EMPTY) {
            return super.getMessage(); // "No error"
        }

        String ownMessage = super.getMessage();
        if (ownMessage != null && !ownMessage.isBlank()) { // Check for blank too
            return ownMessage;
        }
        // If own message is somehow null or blank, try cause's message
        if (Objects.nonNull(cause) && Objects.nonNull(cause.getMessage()) && !cause.getMessage().isBlank()) {
            return cause.getMessage();
        }
        return "unknown failure occurred";
    }

    @Override
    public final Throwable getCause() {
        return this.cause;
    }

//    public Failure wrap(Failure error) {
//        return new Failure(super.getMessage(), error);
//    }
//
    public Failure unwrap() {
        if (cause instanceof Failure) {
            return (Failure) cause;
        }
        return this; // no further unwrapping possible
    }

    public void orThrow() {
        if (isPresent()) {
            throw this;
        }
    }

    public boolean isPresent() {
        return this != EMPTY;
    }

    public boolean isEmpty() {
        return this == EMPTY;
    }

    public boolean isEq(Class<? extends Failure> failureClass) {
        Objects.requireNonNull(failureClass, "class provided is null");
        return isPresent() && failureClass.isInstance(this);
    }

    public void ifPresent(Consumer<Failure> action) {
        Objects.requireNonNull(action);
        if (this.isPresent()) {
            action.accept(this);
        }
    }

    public void ifEmpty(Runnable runnable) {
        Objects.requireNonNull(runnable);
        if (this.isEmpty()) {
            runnable.run();
        }
    }

    public Failure inspectPresent(Consumer<Failure> action) {
        Objects.requireNonNull(action);
        if (this.isPresent()) {
            action.accept(this);
        }
        return this;
    }

    public Failure inspectEmpty(Runnable runnable) {
        Objects.requireNonNull(runnable);
        if (this.isEmpty()) {
            runnable.run();
        }
        return this;
    }


    public static Failure of(Runnable runnable) {
        Objects.requireNonNull(runnable, "runnable is null");
        try {
            runnable.run();
            return Failures.empty();
        } catch (Throwable throwable) {
            String errorMessage = (throwable.getMessage() != null && !throwable.getMessage().isBlank())
                    ? throwable.getMessage()
                    : "An unexpected error occurred during runnable execution.";
            return Failures.wrap(errorMessage, throwable);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Failure failure = (Failure) o;
        if (this == failure) return true;
        if (this == EMPTY || failure == EMPTY) {
            return false;
        }
        return Objects.equals(super.getMessage(), failure.getMessage()) &&
                Objects.equals(cause, failure.cause);
    }

    @Override
    public int hashCode() {
        if (this == EMPTY) {
            return super.hashCode(); // Consistent with EMPTY's identity
        }
        return Objects.hash(super.getMessage(), cause);
    }

    @Override
    public String toString() {
        if (this == EMPTY) {
            return "Failure.EMPTY";
        }
        StringBuilder sb = new StringBuilder(getClass().getSimpleName()).append("{");
        sb.append("message='").append(getMessage()).append("'"); // Use getMessage() for consistent message logic
        if (cause != null && cause != this) { // Avoid infinite recursion if cause references itself
            sb.append(", cause=").append(cause.getClass().getSimpleName());
            if (cause.getMessage() != null && !cause.getMessage().isBlank()) {
                sb.append("('").append(cause.getMessage()).append("')");
            }
        }
        sb.append('}');
        return sb.toString();
    }

}
