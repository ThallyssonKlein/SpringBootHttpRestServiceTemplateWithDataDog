package com.srmasset.domain.loan.service;

import com.srmasset.domain.loan.bo.PersonBO;
import com.srmasset.domain.loan.error.InvalidLoanValueException;
import com.srmasset.domain.loan.error.InvalidNumberOfInstallmentsException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class LoanService {
    public void validateCanCreateLoan(PersonBO personBO, BigDecimal loanValue, Integer numberOfInstallments) {
        if (!(numberOfInstallments <= 24)) {
            throw new InvalidNumberOfInstallmentsException("Invalid number of installments!");
        }

        BigDecimal numberOfInstallmentsBD = new BigDecimal(numberOfInstallments);

        if (loanValue.divide(numberOfInstallmentsBD, BigDecimal.ROUND_HALF_UP).compareTo(personBO.getMinMonthlyValue()) < 0) {            throw new InvalidNumberOfInstallmentsException("Invalid number of installments!");
        }

        if (loanValue.compareTo(personBO.getMaxLoanValue()) > 0) {
            throw new InvalidLoanValueException("Invalid loan value!");
        }
    }
}
