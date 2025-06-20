package io.github.veerakumarak.fp.errors;


import io.github.veerakumarak.fp.Error;

public class EntityAlreadyExists extends Error {
    public EntityAlreadyExists(String message) {
        super(message);
    }
}