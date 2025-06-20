package io.github.veerakumarak.fp.errors;


import io.github.veerakumarak.fp.Failure;

public class EntityValidationFailed extends Failure {
    public EntityValidationFailed(final String message) {
        super(message);
    }
}