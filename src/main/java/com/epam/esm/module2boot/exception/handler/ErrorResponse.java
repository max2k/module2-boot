package com.epam.esm.module2boot.exception.handler;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
public class ErrorResponse {
    private HttpStatus status;
    private String timestamp;
    private List<ErrorDetails> errors;

    @Data
    public static class ErrorDetails {
        private String fieldName;
        private String message;
    }
}