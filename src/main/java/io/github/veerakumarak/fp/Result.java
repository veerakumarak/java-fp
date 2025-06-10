package io.github.veerakumarak.fp;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class Result<T> {
    final private T value;
    final private Error error;

    private Result(T value) {
        this.value = value;
        this.error = null;
    }

    private Result(Error error) {
        this.value = null;
        this.error = error;
    }

    public static <T> Result<T> ok(T value) {
        return new Result<>(Objects.requireNonNull(value));
    }

    public static <T> Result<T> error(Error error) {
        return new Result<T>(error);
    }

    public static <T> Result<T> error(String message) {
        return error(new Error(message));
    }

    public boolean isOk() {
        return !isError();
    }

    public boolean isError() {
        return error != null;
    }

    public boolean isErrorEq(Class<? extends Error> errorClass) {
        return error != null && error.isEq(errorClass);
    }

    public T get() {
        return expect("No value present");
    }
    public T expect(String message) {
        if (isError()) {
            throw new NoSuchElementException(message);
        }
        return value;
    }
    public Error error() {
        return error;
    }

    public void ifOk(Consumer<? super T> action) {
        if (value != null) {
            action.accept(value);
        }
    }

    static <T> Result<T> of(Supplier<T> supplier) {
        Objects.requireNonNull(supplier, "supplier is null");
        try {
            return Result.ok(supplier.get());
        } catch (Throwable var2) {
            Throwable t = var2;
            return Result.error(t.toString());
        }
    }

    public <U> Result<U> map(Function<T, U> mapper) {
        Objects.requireNonNull(mapper, "mapper is null");
        if (this.isError()) {
            return Result.error(error());
        } else {
            try {
                return ok(mapper.apply(this.get()));
            } catch (Throwable var3) {
                Throwable t = var3;
                return error(t.toString());
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
            } catch (Throwable var3) {
                Throwable t = var3;
                return error(t.toString());
            }
        }
    }

}

