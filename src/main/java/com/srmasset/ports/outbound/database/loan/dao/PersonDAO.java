package com.srmasset.ports.outbound.database.loan.dao;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "PERSON")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PersonDAO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
}
