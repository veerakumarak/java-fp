package io.github.veerakumarak.fp.errors;


import io.github.veerakumarak.fp.Error;

public class EntityValidationFailed extends Error {
    public EntityValidationFailed(final String message) {
        super(message);
    }
}