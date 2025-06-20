package io.github.veerakumarak.fp.errors;


import io.github.veerakumarak.fp.BaseError;

public class EntityAlreadyExists extends BaseError {
    public EntityAlreadyExists(String message) {
        super(message);
    }
}