package com.srmasset.ports.inbound.http.controllers;

import com.srmasset.ports.inbound.http.error.HttpException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.http.HttpStatus;

@RestControllerAdvice
public class GlobalErrorHandler {
    private static final String GENERIC_ERROR_MESSAGE = "Internal Server Error!";

    @ExceptionHandler(HttpException.class)
    public ResponseEntity<Object> handleHttpException(HttpException ex, WebRequest request) {
        return ResponseEntity
                .status(ex.getStatus())
                .body(new ErrorResponse(ex.getMessage(), ex.getClass().getSimpleName(), ex.getStatus()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex, WebRequest request) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(GENERIC_ERROR_MESSAGE, ex.getClass().getSimpleName(), 500));
    }

    @AllArgsConstructor
    @Getter
    @Setter
    private static class ErrorResponse {
        private String message;
        private String name;
        private int status;
    }
}
