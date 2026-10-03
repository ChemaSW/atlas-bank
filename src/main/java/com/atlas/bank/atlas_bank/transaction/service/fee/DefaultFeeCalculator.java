package com.atlas.bank.atlas_bank.transaction.service.fee;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component      //CUENTA POR DEFECTO - para cuentas que no son de ahorro ni cuenta normal
public class DefaultFeeCalculator implements FeeCalculator{
    @Override
    public boolean supports(String accountType) {
        return true;
    }

    @Override
    public BigDecimal calculate(BigDecimal amount) {
        return BigDecimal.ZERO;
    }
}
