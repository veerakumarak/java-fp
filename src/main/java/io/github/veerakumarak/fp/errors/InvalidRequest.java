package io.github.veerakumarak.fp.errors;

import io.github.veerakumarak.fp.Error;

public class InvalidRequest extends Error {
    public InvalidRequest(final String message) {
        super(message);
    }
}