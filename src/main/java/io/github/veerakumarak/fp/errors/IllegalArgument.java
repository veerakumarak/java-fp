package io.github.veerakumarak.fp.errors;

import io.github.veerakumarak.fp.Failure;

public class IllegalArgument extends Failure {
    public IllegalArgument(final String message) {
        super(message);
    }
}