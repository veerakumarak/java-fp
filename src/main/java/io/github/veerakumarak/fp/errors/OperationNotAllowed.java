package io.github.veerakumarak.fp.errors;

import io.github.veerakumarak.fp.Error;

public class OperationNotAllowed extends Error {
    public OperationNotAllowed(final String message) {
        super(message);
    }
}