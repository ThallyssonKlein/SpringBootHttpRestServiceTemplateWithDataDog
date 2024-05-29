package com.srmasset.ports.inbound.http.error;

public class NotFoundException extends HttpException {
    public NotFoundException(String message) {
        super(message, "NotFound", 404);
    }
}
