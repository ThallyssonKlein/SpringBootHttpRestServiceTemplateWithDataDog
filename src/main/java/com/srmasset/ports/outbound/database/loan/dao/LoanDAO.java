package com.srmasset.ports.outbound.database.loan.dao;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "LOAN")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class LoanDAO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(nullable = false, precision = 18, scale = 4, name = "LOAN_VALUE")
    private BigDecimal value;

    @Column(nullable = false, name = "NUMBER_OF_INSTALLMENTS")
    private Integer numberOfInstallments;

    @Column(nullable = false, name = "PAYMENT_STATUS")
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus = PaymentStatus.PENDING;

    @CreationTimestamp
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @ManyToOne
    @JoinColumn(name = "PERSON_ID", nullable = false)
    private PersonDAO person;
}
