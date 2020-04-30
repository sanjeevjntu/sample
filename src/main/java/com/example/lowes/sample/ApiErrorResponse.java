package com.example.lowes.sample;

import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Builder
public class ApiErrorResponse {
    private String errorMessage;
    private String context;
    private OffsetDateTime timestamp;
}
