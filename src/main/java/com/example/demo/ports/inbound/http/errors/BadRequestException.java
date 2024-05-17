package com.example.demo.ports.inbound.http.errors;

public class BadRequestException extends HttpException {
    public BadRequestException(String message) {
        super(message, "BadRequestError", 400);
    }
}
