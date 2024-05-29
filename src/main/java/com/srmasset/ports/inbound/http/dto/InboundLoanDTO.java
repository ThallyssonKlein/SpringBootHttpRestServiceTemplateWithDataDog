package com.srmasset.ports.inbound.http.dto;


import lombok.*;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class InboundLoanDTO {

    @NotNull(message = "Value cannot be null")
    @DecimalMin(value = "0.0001", message = "Loan value must be greater than 0.0001")
    @DecimalMax(value = "99999999999999.9999", message = "Loan value must be less than or equal to 99999999999999.9999")
    @Digits(integer = 14, fraction = 4, message = "Loan value must have up to 14 integer digits and 4 fraction digits")
    private BigDecimal loanValue;

    @NotNull(message = "Number of installments cannot be null")
    private Integer numberOfInstallments;
}
