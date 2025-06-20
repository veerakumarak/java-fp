package io.github.veerakumarak.fp.errors;

import io.github.veerakumarak.fp.BaseError;

public class EntityNotFound extends BaseError {
    public EntityNotFound(final String message) {
        super(message);
    }
}