package com.example.demo.ports.inbound.http.errors;

public class InternalErrorException extends HttpException {
    public InternalErrorException(String message) {
        super(message, "InternalError", 500);
    }
}
