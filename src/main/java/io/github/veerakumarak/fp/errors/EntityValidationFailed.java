package io.github.veerakumarak.fp.errors;


import io.github.veerakumarak.fp.BaseError;

public class EntityValidationFailed extends BaseError {
    public EntityValidationFailed(final String message) {
        super(message);
    }
}