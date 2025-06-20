package io.github.veerakumarak.fp.errors;

import io.github.veerakumarak.fp.Failure;

public class OperationNotAllowed extends Failure {
    public OperationNotAllowed(final String message) {
        super(message);
    }
}