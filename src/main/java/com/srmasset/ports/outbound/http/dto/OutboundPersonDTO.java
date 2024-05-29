package com.srmasset.ports.outbound.http.dto;


import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OutboundPersonDTO {
    private Long id;

    private BigDecimal minMonthlyValue;

    private BigDecimal maxLoanValue;

}
