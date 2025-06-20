package io.github.veerakumarak.fp.failures;


import io.github.veerakumarak.fp.Failure;

public class EntityAlreadyExists extends Failure {
    public EntityAlreadyExists(String message) {
        super(message);
    }
}