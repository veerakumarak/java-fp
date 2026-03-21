package io.github.veerakumarak.fp.failures;

import io.github.veerakumarak.fp.Failure;

public class IllegalState extends Failure {
    public IllegalState(final String message) {
        super(message);
    }
}