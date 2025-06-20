package io.github.veerakumarak.fp.errors;

import io.github.veerakumarak.fp.BaseError;

import java.util.List;
import java.util.Map;

public class InvalidRequest extends BaseError {
    private final Map<String, List<String>> reasons;
    public InvalidRequest(final String message) {
        super(message);
        reasons = null;
    }

    public InvalidRequest(Map<String, List<String>> reasons) {
        super(null);
        this.reasons = reasons;
    }

    public Map<String, List<String>> getReasons() {
        return reasons;
    }
}