package com.srmasset.ports.inbound.http.error;

public class InternalErrorException extends HttpException {
    public InternalErrorException(String message) {
        super(message, "InternalError", 500);
    }
}
