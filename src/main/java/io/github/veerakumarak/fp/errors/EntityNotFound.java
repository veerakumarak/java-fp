package io.github.veerakumarak.fp.errors;

import io.github.veerakumarak.fp.Error;

public class EntityNotFound extends Error {
    public EntityNotFound(final String message) {
        super(message);
    }
}