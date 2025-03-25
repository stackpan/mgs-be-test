package io.github.stackpan.mgs_be_test.exception;

public class InvalidStorageUploadException extends RuntimeException {

    private final String fieldNameContext;

    public InvalidStorageUploadException(String message, String fieldNameContext) {
        super(message);
        this.fieldNameContext = fieldNameContext;
    }

    public String getFieldNameContext() {
        return fieldNameContext;
    }
}
