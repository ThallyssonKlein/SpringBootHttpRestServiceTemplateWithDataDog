package com.srmasset.adapters.outbound.database;

import com.srmasset.ports.inbound.http.dto.InboundLoanDTO;
import com.srmasset.ports.inbound.http.error.NotFoundException;
import com.srmasset.ports.outbound.database.loan.OutboundLoanRepositoryPort;
import com.srmasset.ports.outbound.database.loan.dao.LoanDAO;
import com.srmasset.ports.outbound.database.loan.dao.PaymentStatus;
import com.srmasset.ports.inbound.http.error.InternalErrorException;
import com.srmasset.ports.outbound.database.loan.dao.PersonDAO;
import com.srmasset.ports.outbound.http.dto.OutboundPersonDTO;
import datadog.trace.api.Trace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class OutboundLoanAdapter {

    @Autowired
    private OutboundLoanRepositoryPort outboundLoanRepositoryPort;

    private PersonDAO convertOutboundPersonDTOToPersonDAO(OutboundPersonDTO outboundPersonDTO) {
        PersonDAO personDAO = new PersonDAO();
        personDAO.setId(outboundPersonDTO.getId());
        return personDAO;
    }

    @Trace(operationName = "OutboundLoanAdapter.createLoan")
    public LoanDAO createLoan(InboundLoanDTO inboundLoanDTO, OutboundPersonDTO outboundPersonDTO) throws InternalErrorException {
        try {
            LoanDAO loanDAO = new LoanDAO();
            loanDAO.setPerson(this.convertOutboundPersonDTOToPersonDAO(outboundPersonDTO));
            loanDAO.setValue(inboundLoanDTO.getLoanValue());
            loanDAO.setNumberOfInstallments(inboundLoanDTO.getNumberOfInstallments());
            return this.outboundLoanRepositoryPort.save(loanDAO);
        } catch (Exception e) {
            throw new InternalErrorException("Unexpected error saving loan");
        }
    }

    @Trace(operationName = "OutboundLoanAdapter.payLoan")
    public void payLoan(Long loanId) throws InternalErrorException {
        try {
            Optional<LoanDAO> loanDAO = this.outboundLoanRepositoryPort.findById(loanId);
            if (loanDAO.isPresent()) {
                loanDAO.get().setPaymentStatus(PaymentStatus.PAID);
                this.outboundLoanRepositoryPort.save(loanDAO.get());
            } else {
                throw new NotFoundException("Loan not found!");
            }
        } catch (Exception e) {
            throw new InternalErrorException("Unexpected error paying loan");
        }
    }
}
