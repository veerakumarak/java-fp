package io.github.veerakumarak.fp;

/**
 * Represents a supplier of results that can throw an exception.
 *
 * @param <T> the type of results supplied by this supplier
 */
@FunctionalInterface
public interface ThrowingSupplier<T> {
    T get() throws Exception;
}