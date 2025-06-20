package io.github.veerakumarak.fp.failures;

import io.github.veerakumarak.fp.Failure;

import java.util.List;
import java.util.Map;

public class InvalidRequest extends Failure {
    private final Map<String, List<String>> reasons;
    public InvalidRequest(final String message) {
        super(message);
        reasons = null;
    }

    public InvalidRequest(String key, String reason) {
        super(null);
        this.reasons = Map.of(key, List.of(reason));
    }

    public InvalidRequest(Map<String, List<String>> reasons) {
        super(null);
        this.reasons = reasons;
    }

    public Map<String, List<String>> getReasons() {
        return reasons;
    }
}