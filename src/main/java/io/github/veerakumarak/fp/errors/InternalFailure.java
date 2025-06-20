package io.github.veerakumarak.fp.errors;

import io.github.veerakumarak.fp.Failure;

public class InternalFailure extends Failure {
    public InternalFailure(final String message) {
        super(message);
    }
}