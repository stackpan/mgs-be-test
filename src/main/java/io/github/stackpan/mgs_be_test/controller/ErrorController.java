package io.github.stackpan.mgs_be_test.controller;

import io.github.stackpan.mgs_be_test.exception.InvalidDtoException;
import io.github.stackpan.mgs_be_test.exception.InvalidFileTypeException;
import io.github.stackpan.mgs_be_test.exception.InvalidStorageUploadException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.*;

@RestControllerAdvice
public class ErrorController extends ResponseEntityExceptionHandler {

    private record BaseErrorResponse(String title, Object details) {
    }

    @ExceptionHandler(InvalidDtoException.class)
    public ResponseEntity<Object> handleDtoFieldException(InvalidDtoException ex, WebRequest request) {
        var response = new BaseErrorResponse(ex.getMessage(), ex.getDetails());

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(InvalidStorageUploadException.class)
    public ResponseEntity<Object> handleStorageUploadException(InvalidStorageUploadException ex) {
        BaseErrorResponse response;

        var title = "Invalid payload.";

        if (ex instanceof InvalidFileTypeException) {
            response = new BaseErrorResponse(title, Map.of(
                    ex.getFieldNameContext(),
                    "must be allowed file type: " + String.join(", ", ((InvalidFileTypeException) ex).getAllowedExtensions()))
            );
        } else {
            response = new BaseErrorResponse(title, Map.of(ex.getFieldNameContext(), ex.getMessage()));
        }

        return ResponseEntity.badRequest().body(response);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        var errors = ex.getBindingResult().getAllErrors();

        var errorMap = new HashMap<String, List<String>>();
        errors.forEach(error -> errorMap.put(
                ((FieldError) error).getField(),
                List.of(Objects.requireNonNull(error.getDefaultMessage()))
        ));

        var response = new BaseErrorResponse("Invalid payload.", errorMap);
        return ResponseEntity.badRequest().body(response);
    }
}
