package com.srmasset.ports.inbound.http.error;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class HttpException extends Exception {
    private int status;
    private String name;

    public HttpException(String message, String name, int status) {
        super(message);
        this.status = status;
        this.name = name;
    }
}
