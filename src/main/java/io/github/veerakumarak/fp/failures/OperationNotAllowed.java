package io.github.veerakumarak.fp.failures;

import io.github.veerakumarak.fp.Failure;

public class OperationNotAllowed extends Failure {
    public OperationNotAllowed(final String message) {
        super(message);
    }
}