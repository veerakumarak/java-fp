package io.github.veerakumarak.fp;

/**
 * Represents an operation that does not return a result.
 *
 */
@FunctionalInterface
public interface ThrowingRunnable {
    void run() throws Exception;
}