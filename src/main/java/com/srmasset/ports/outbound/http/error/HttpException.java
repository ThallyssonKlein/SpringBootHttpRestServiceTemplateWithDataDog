package com.srmasset.ports.outbound.http.error;

import lombok.Getter;

@Getter
public class HttpException extends Exception {
    private final int statusCode;

    public HttpException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }
}