package com.atlas.bank.atlas_bank.transaction.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferRequest {

        //NotBlank esta solo pensado para STRING
    @NotNull(message = "La cuenta de origen es obligatoria")
    private Long fromAccountId;

    @NotNull(message = "La cuenta de destino es obligatoria")
    private Long toAccountId;

    @NotNull(message = "El monto es obligatorio")
    @Positive(message = "El monto debe ser mayor a cero")
    private BigDecimal amount;
}
