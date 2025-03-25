package io.github.stackpan.mgs_be_test.exception;

public class InvalidStorageUploadException extends RuntimeException {
    public InvalidStorageUploadException(String message) {
        super(message);
    }
}
