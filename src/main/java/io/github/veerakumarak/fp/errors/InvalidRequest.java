package io.github.veerakumarak.fp.errors;

import io.github.veerakumarak.fp.BaseError;

public class InvalidRequest extends BaseError {
    public InvalidRequest(final String message) {
        super(message);
    }
}