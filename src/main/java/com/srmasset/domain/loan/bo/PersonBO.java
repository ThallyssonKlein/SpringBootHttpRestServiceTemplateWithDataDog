package com.srmasset.domain.loan.bo;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class PersonBO {
    private BigDecimal minMonthlyValue;

    private BigDecimal maxLoanValue;
}
