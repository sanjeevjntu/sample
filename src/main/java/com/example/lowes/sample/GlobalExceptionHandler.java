package com.example.lowes.sample;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<List<ApiErrorResponse>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        HashSet<ApiErrorResponse> apiErrorResponses = new HashSet<>();
        ex.getBindingResult()
                .getAllErrors()
                .forEach(objectError -> {
                    FieldError fieldError = (FieldError) objectError;
                    ApiErrorResponse errorResponse = getApiErrorResponse(ex.getMessage());
                    errorResponse.setContext(fieldError.getObjectName() + "." +
                            fieldError.getField() + " : rejected value is : " + fieldError.getRejectedValue()
                    );
                    apiErrorResponses.add(errorResponse);
                });

        return ResponseEntity.badRequest().body(new ArrayList<>(apiErrorResponses));
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public ResponseEntity<ApiErrorResponse> handleRuntimeException(IllegalArgumentException ex) {
        ApiErrorResponse errorResponse = getApiErrorResponse(ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    @ExceptionHandler(value = {RuntimeException.class})
    public ResponseEntity<ApiErrorResponse> handleRuntimeException(RuntimeException ex) {
        ApiErrorResponse errorResponse = getApiErrorResponse(ex.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorResponse);
    }

    private ApiErrorResponse getApiErrorResponse(String message) {
        return ApiErrorResponse.builder()
                .errorMessage(message)
                .timestamp(OffsetDateTime.now(ZoneOffset.UTC))
                .build();
    }
}


