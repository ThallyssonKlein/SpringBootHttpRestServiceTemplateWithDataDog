package com.srmasset.adapters.inbound.http;

import com.srmasset.adapters.outbound.database.OutboundLoanAdapter;
import com.srmasset.adapters.outbound.http.OutboundPersonAdapter;
import com.srmasset.domain.loan.bo.PersonBO;
import com.srmasset.domain.loan.error.InvalidLoanValueException;
import com.srmasset.domain.loan.error.InvalidNumberOfInstallmentsException;
import com.srmasset.domain.loan.service.LoanService;
import com.srmasset.ports.inbound.http.dto.InboundLoanDTO;
import com.srmasset.ports.inbound.http.error.BadRequestException;
import com.srmasset.ports.inbound.http.error.InternalErrorException;
import com.srmasset.ports.inbound.http.error.NotFoundException;
import com.srmasset.ports.outbound.database.loan.dao.LoanDAO;
import com.srmasset.ports.outbound.http.dto.OutboundPersonDTO;
import com.srmasset.ports.outbound.observability.MetricCollector;
import datadog.trace.api.Trace;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class InboundLoanAdapter {

    @Autowired
    private OutboundPersonAdapter outboundPersonAdapter;

    @Autowired
    private LoanService loanService;

    @Autowired
    private OutboundLoanAdapter outboundLoanAdapter;

    @Autowired
    private MetricCollector metricCollector;

    @Trace(operationName = "InboundLoanAdapter.requestLoan")
    public LoanDAO requestLoan(Long id, InboundLoanDTO inboundLoanDTO) throws InternalErrorException, NotFoundException, BadRequestException {
        OutboundPersonDTO personDTO = this.outboundPersonAdapter.findPersonById(id);

        if (personDTO == null) {
            this.metricCollector.incrementMetric("person_not_found");
            throw new NotFoundException("Person not found!");
        }

        try {
            this.loanService.validateCanCreateLoan(
                    new PersonBO(personDTO.getMinMonthlyValue(), personDTO.getMaxLoanValue()),
                    inboundLoanDTO.getLoanValue(), inboundLoanDTO.getNumberOfInstallments());
        } catch (InvalidLoanValueException | InvalidNumberOfInstallmentsException e) {
            HashMap<String, String> metricTags = new HashMap<>();
            metricTags.put("person_id", id.toString());
            metricTags.put("loan_value", inboundLoanDTO.getLoanValue().toString());
            metricTags.put("monthly_value", inboundLoanDTO.getNumberOfInstallments().toString());
            Map<String, String> logTags = Map.of("person_id", id.toString(), "loan_value", inboundLoanDTO.getLoanValue().toString(), "monthly_value", inboundLoanDTO.getNumberOfInstallments().toString());

            log.error("Loan not allowed: {}", logTags);

            this.metricCollector.incrementMetric("loan_not_allowed", metricTags);
            throw new BadRequestException(e.getMessage());
        }


        return this.outboundLoanAdapter.createLoan(inboundLoanDTO, personDTO);
    }
}