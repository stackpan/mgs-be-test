package io.github.stackpan.mgs_be_test.exception;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class InvalidDtoException extends RuntimeException {

    @Getter
    private final Map<String, String[]> details = new HashMap<>();

    public InvalidDtoException() {
        super("Invalid field.");
    }

    public void putDetail(String fieldName, String invalidDetails) {
        details.put(fieldName, new String[] {invalidDetails});
    }
}
