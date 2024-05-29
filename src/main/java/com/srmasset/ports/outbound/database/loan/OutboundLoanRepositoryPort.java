package com.srmasset.ports.outbound.database.loan;

import com.srmasset.ports.outbound.database.loan.dao.LoanDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutboundLoanRepositoryPort extends JpaRepository<LoanDAO, Long>{
}
