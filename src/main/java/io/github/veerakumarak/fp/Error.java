package io.github.veerakumarak.fp;

import java.util.Objects;
import java.util.function.Supplier;

public class Error {

    final private String message;

    public Error(String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }

    static <T> Error of(Supplier<T> supplier) {
        Objects.requireNonNull(supplier, "supplier is null");

        try {
            return new Success(supplier.apply());
        } catch (Throwable var2) {
            Throwable t = var2;
            return new Failure(t);
        }
    }

}
