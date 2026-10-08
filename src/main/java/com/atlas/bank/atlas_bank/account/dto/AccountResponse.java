package com.atlas.bank.atlas_bank.account.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class AccountResponse { //DTO de salida, son datos que el cliente necesita ver, pero nosotros controlamos exactamente que campos van
    private Long id;
    private String accountNumber;
    private String ownerName;
    private String email;
    private String type; // Saving, Cheking (cuenta de ahorro, cuenta corriente)
    private BigDecimal balance;
    private String status; // Active, Closed, Frozen
    private LocalDateTime createAt;
}
