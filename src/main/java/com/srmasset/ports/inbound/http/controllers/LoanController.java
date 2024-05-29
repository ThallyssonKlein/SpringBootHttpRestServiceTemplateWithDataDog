package com.srmasset.ports.inbound.http.controllers;

import com.srmasset.adapters.inbound.http.InboundLoanAdapter;
import com.srmasset.adapters.outbound.database.OutboundLoanAdapter;
import com.srmasset.ports.inbound.http.dto.InboundLoanDTO;
import com.srmasset.ports.outbound.database.loan.dao.LoanDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import datadog.trace.api.Trace;

@RestController
@RequestMapping("/loan")
public class LoanController {

    @Autowired
    private InboundLoanAdapter inboundPersonAdapter;

    @Autowired
    private OutboundLoanAdapter outboundLoanAdapter;

    @PostMapping("/request/{userId}")
    @Trace(operationName = "LoanController.requestLoan")
    public ResponseEntity<LoanDAO> requestLoan(@PathVariable Long userId, @RequestBody InboundLoanDTO inboundLoanDTO) throws Exception {
        return ResponseEntity.ok(this.inboundPersonAdapter.requestLoan(userId, inboundLoanDTO));
    }

    @PostMapping("/pay/{loanId}")
    @Trace(operationName = "LoanController.payLoan")
    public ResponseEntity<Void> payLoan(@PathVariable Long loanId) throws Exception {
        this.outboundLoanAdapter.payLoan(loanId);
        return ResponseEntity.ok().build();
    }
}