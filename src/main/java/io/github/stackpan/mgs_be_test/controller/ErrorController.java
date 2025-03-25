package io.github.stackpan.mgs_be_test.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@RestControllerAdvice
public class ErrorController extends ResponseEntityExceptionHandler {

    private record BaseErrorResponse(String message, Object errors) {
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
