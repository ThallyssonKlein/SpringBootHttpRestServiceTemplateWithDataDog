package com.srmasset.domain.loan.error;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class InvalidLoanValueException extends RuntimeException {
    private final String name = "InvalidLoanValue";

    public InvalidLoanValueException(String message) {
        super(message);
    }
}