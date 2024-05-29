package com.srmasset.domain.loan.error;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class InvalidNumberOfInstallmentsException extends RuntimeException {
    private final String name = "InvalidNumberOfInstallments";

    public InvalidNumberOfInstallmentsException(String message) {
        super(message);
    }
}
