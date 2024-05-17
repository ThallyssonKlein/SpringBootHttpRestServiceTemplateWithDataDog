package com.example.demo.ports.inbound.http.errors;

public class ForbiddenException extends HttpException {
    public ForbiddenException(String message) {
        super(message, "ForbiddenException", 403);
    }
}
