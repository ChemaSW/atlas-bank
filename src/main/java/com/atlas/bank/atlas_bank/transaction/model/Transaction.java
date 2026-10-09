package com.atlas.bank.atlas_bank.transaction.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
//@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Transaction {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include
    private Long id;

    private String type; // DEPOSIT, WITHDRAWAL, TRANSFER

    private Long sourceAccountId;

    private Long targetAccountId;

    private BigDecimal amount;

    private BigDecimal fee;

    private String status; // PENDING, EXECUTED, REJECTED

    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now(); //Fecha de Hoy
        if (this.status == null) this.status = "EXECUTED";
    }
}
