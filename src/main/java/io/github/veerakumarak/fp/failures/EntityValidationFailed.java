package io.github.veerakumarak.fp.failures;


import io.github.veerakumarak.fp.Failure;

public class EntityValidationFailed extends Failure {
    public EntityValidationFailed(final String message) {
        super(message);
    }
}