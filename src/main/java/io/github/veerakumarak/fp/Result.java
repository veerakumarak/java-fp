package io.github.veerakumarak.fp;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class Result<T> {
    final private T value;
    final private Error error;

    private Result(T value) {
        this.value = value;
        this.error = Error.empty();
    }

    private Result(Error error) {
        this.value = null;
        this.error = error;
    }

    public static <T> Result<T> ok(T value) {
        return new Result<>(Objects.requireNonNull(value));
    }

    public static <T> Result<T> error(Error error) {
        return new Result<>(error);
    }

    public static <T> Result<T> error(String message) {
        return error(Error.with(message));
    }

    public boolean isOk() {
        return error.isEmpty();
    }

    public boolean isError() {
        return !isOk();
    }

    public boolean isErrorEq(Class<? extends Error> errorClass) {
        return isError() && error.isEq(errorClass);
    }

    public Error error() {
        return error;
    }

    public T expect(String message) {
        if (isError()) {
            throw Error.with(message);
        }
        return value;
    }
    public T get() {
        return expect("No value present");
    }

    public T orElse(T defaultValue) {
        return isOk() ? value : defaultValue;
    }

    public T orElseGet(Supplier<T> supplier) {
        return isOk() ? value : supplier.get();
    }

    public void ifOk(Consumer<? super T> action) {
        if (this.isOk()) {
            action.accept(value);
        }
    }

    public void ifError(Consumer<Error> action) {
        if (this.isError()) {
            action.accept(error);
        }
    }

    public Result<T> inspectOk(Consumer<? super T> action) {
        if (this.isOk()) {
            action.accept(value);
        }
        return this;
    }

    public Result<T> inspectError(Consumer<Error> action) {
        if (this.isError()) {
            action.accept(error);
        }
        return this;
    }

    public static <T> Result<T> of(Supplier<T> supplier) {
        Objects.requireNonNull(supplier, "supplier is null");
        try {
            return Result.ok(supplier.get());
        } catch (Throwable throwable) {
            return Result.error(throwable.toString());
        }
    }

    public <U> Result<U> map(Function<T, U> mapper) {
        Objects.requireNonNull(mapper, "mapper is null");
        if (this.isError()) {
            return Result.error(error());
        } else {
            try {
                return ok(mapper.apply(this.get()));
            } catch (Throwable throwable) {
                return error(throwable.toString());
            }
        }
    }

    public <U> Result<U> flatMap(Function<T, Result<U>> mapper) {
        Objects.requireNonNull(mapper, "mapper is null");
        if (this.isError()) {
            return Result.error(error());
        } else {
            try {
                return mapper.apply(this.get());
            } catch (Throwable throwable) {
                return error(throwable.toString());
            }
        }
    }

}
