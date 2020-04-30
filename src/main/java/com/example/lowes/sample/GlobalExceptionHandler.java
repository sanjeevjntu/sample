package com.example.lowes.sample;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    @ResponseBody
    public ResponseEntity<List<ApiErrorResponse>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        HashSet<ApiErrorResponse> apiErrorResponses = new HashSet<>();
        ex.getBindingResult()
                .getAllErrors()
                .forEach(objectError -> {
                    FieldError fieldError = (FieldError) objectError;
                    ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder()
                            .errorMessage(fieldError.getDefaultMessage())
                            .context(fieldError.getObjectName() + "." +
                                    fieldError.getField() + " : rejected value is : " + fieldError.getRejectedValue()
                            )
                            .offsetDateTime(OffsetDateTime.now(ZoneOffset.UTC))
                            .build();
                    apiErrorResponses.add(apiErrorResponse);
                });

        return ResponseEntity.badRequest().body(new ArrayList<>(apiErrorResponses));
    }
}
