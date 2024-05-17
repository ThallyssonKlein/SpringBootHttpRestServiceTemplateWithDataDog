package com.example.demo.domain.user.errors;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class NotAllowedToBeFoundException extends RuntimeException {
    private final String name = "NotAllowedToBeFound";

    public NotAllowedToBeFoundException(String message) {
        super(message);
    }
}

