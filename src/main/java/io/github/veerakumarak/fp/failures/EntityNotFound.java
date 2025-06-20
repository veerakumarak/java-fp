package io.github.veerakumarak.fp.failures;

import io.github.veerakumarak.fp.Failure;

public class EntityNotFound extends Failure {
    public EntityNotFound(final String message) {
        super(message);
    }
}