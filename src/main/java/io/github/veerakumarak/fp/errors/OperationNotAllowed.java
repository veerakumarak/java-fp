package io.github.veerakumarak.fp.errors;

import io.github.veerakumarak.fp.BaseError;

public class OperationNotAllowed extends BaseError {
    public OperationNotAllowed(final String message) {
        super(message);
    }
}