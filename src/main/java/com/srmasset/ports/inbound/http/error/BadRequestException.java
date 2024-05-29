package com.srmasset.ports.inbound.http.error;

public class BadRequestException extends HttpException {
    public BadRequestException(String message) {
        super(message, "BadRequestError", 400);
    }
}
