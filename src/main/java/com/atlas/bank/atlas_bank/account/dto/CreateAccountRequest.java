package com.atlas.bank.atlas_bank.account.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

//@Data - Genera Getter, Setter, toString, equal, hashCode
@Data
public class CreateAccountRequest { //Solicitud del usuario/cliente

    @NotBlank(message = "El número de cuenta es obligatorio")
    private String accountNumber;

    @NotBlank(message = "Debe digitar al menos un nombre")
    //@Size(min = 10, max = 30)
    private String ownerName;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email no tiene un formato válido")
    private String email;

    @NotBlank(message = "El tipo de cuenta es obligatorio")
    private String type; // Saving, Cheking (cuenta de ahorro, cuenta corriente)

    @PositiveOrZero(message = "El balance no puede ser negativo")
    private BigDecimal balance;
}
