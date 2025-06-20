package io.github.veerakumarak.fp.errors;


import io.github.veerakumarak.fp.Failure;

public class EntityAlreadyExists extends Failure {
    public EntityAlreadyExists(String message) {
        super(message);
    }
}