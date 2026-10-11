package com.atlas.bank.atlas_bank.account.dto;

import lombok.Data;

import java.math.BigDecimal;

//@Data - Genera Getter, Setter, toString, equal, hashCode
@Data
public class CreateAccountRequest { //Solicitud del usuario/cliente
    private String accountNumber;
    private String ownerName;
    private String email;
    private String type; // Saving, Cheking (cuenta de ahorro, cuenta corriente)
    private BigDecimal balance;
}
