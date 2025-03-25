package io.github.stackpan.mgs_be_test.exception;

import java.util.Arrays;

public class InvalidFileTypeException extends InvalidStorageUploadException {

    private final String[] allowedExtensions;

    public InvalidFileTypeException(String fieldNameContext, String... allowedExtensions) {
        super("Invalid file type. Allowed: %s".formatted(Arrays.toString(allowedExtensions)), fieldNameContext);
        this.allowedExtensions = allowedExtensions;
    }

    public String[] getAllowedExtensions() {
        return allowedExtensions;
    }
}
