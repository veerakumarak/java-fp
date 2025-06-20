package io.github.veerakumarak.fp;

import io.github.veerakumarak.fp.errors.IllegalArgument;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public final class Result<T> {
    final private T value;
    final private Failure failure;

    private Result(T value) {
        this.value = value;
        this.failure = Failure.empty();
    }

    private Result(Failure failure) {
        this.value = null;
        this.failure = Objects.requireNonNull(failure, "Failure object cannot be null for an error Result.");
        if (failure.isEmpty()) {
            throw new IllegalArgument("Cannot create a failure Result with an empty Failure object. Use Result.ok() instead.");
        }
    }

    public static <T> Result<T> ok(T value) {
        return new Result<>(Objects.requireNonNull(value));
    }

    public static <T> Result<T> failure(Failure error) {
        return new Result<>(error);
    }

    public static <T> Result<T> failure(String message) {
        return failure(Failure.with(message));
    }

    public boolean isOk() {
        return failure.isEmpty();
    }

    public boolean isFailure() {
        return !isOk();
    }

    public boolean isErrorEq(Class<? extends Failure> failureClass) {
        return isFailure() && failure.isEq(failureClass);
    }

    public Failure failure() {
        if (isOk()) {
            throw new IllegalArgument("Cannot get failure from a successful Result.");
        }
        return failure;
    }

    public T expect(String message) {
        if (isFailure()) {
            throw Failure.wrap(message + ": " + failure.getMessage(), failure);
        }
        return value;
    }
    public T get() {
        return expect("No value present, Result is in an failure state.");
    }

    public T orElse(T defaultValue) {
        return isOk() ? value : defaultValue;
    }

    public T orElseGet(Supplier<T> supplier) {
        return isOk() ? value : supplier.get();
    }

    public T orElseThrow() {
        if (isFailure()) {
            throw failure();
        }
        return value;
    }

    public <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
        if (value != null) {
            return value;
        } else {
            throw exceptionSupplier.get();
        }
    }

    public void ifOk(Consumer<? super T> action) {
        Objects.requireNonNull(action);
        if (this.isOk()) {
            action.accept(value);
        }
    }

    public void ifFailure(Consumer<Failure> action) {
        Objects.requireNonNull(action);
        if (this.isFailure()) {
            action.accept(failure);
        }
    }

    public Result<T> inspectOk(Consumer<? super T> action) {
        Objects.requireNonNull(action);
        if (this.isOk()) {
            action.accept(value);
        }
        return this;
    }

    public Result<T> inspectFailure(Consumer<Failure> action) {
        Objects.requireNonNull(action);
        if (this.isFailure()) {
            action.accept(failure);
        }
        return this;
    }

    public static <T> Result<T> of(Supplier<T> supplier) {
        Objects.requireNonNull(supplier, "supplier is null");
        try {
            return Result.ok(supplier.get());
        } catch (Throwable throwable) {
            String errorMessage = Optional.ofNullable(throwable.getMessage())
                    .orElse("An unexpected error occurred during supplier execution.");
            return failure(Failure.wrap(errorMessage, throwable));
        }
    }

    public <U> Result<U> map(Function<T, U> mapper) {
        Objects.requireNonNull(mapper, "mapper is null");
        if (this.isFailure()) {
            return Result.failure(failure());
        } else {
            try {
                return ok(mapper.apply(this.value));
            } catch (Throwable throwable) {
                String errorMessage = Optional.ofNullable(throwable.getMessage())
                        .orElse("An error occurred during mapping.");
                return failure(Failure.wrap(errorMessage, throwable));
            }
        }
    }

    public <U> Result<U> flatMap(Function<T, Result<U>> mapper) {
        Objects.requireNonNull(mapper, "mapper is null");
        if (this.isFailure()) {
            return Result.failure(failure());
        } else {
            try {
                return mapper.apply(this.value);
            } catch (Throwable throwable) {
                String errorMessage = Optional.ofNullable(throwable.getMessage())
                        .orElse("An error occurred during flatMapping.");
                return failure(Failure.wrap(errorMessage, throwable));
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Result<?> result = (Result<?>) o;
        if (isOk() && result.isOk()) {
            return Objects.equals(value, result.value);
        }
        else if (isFailure() && result.isFailure()) {
            return Objects.equals(failure, result.failure);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, failure);
    }

    @Override
    public String toString() {
        if (isOk()) {
            return "Result.Ok(" + value + ")";
        } else {
            return "Result.Failure(" + failure + ")";
        }
    }

}
