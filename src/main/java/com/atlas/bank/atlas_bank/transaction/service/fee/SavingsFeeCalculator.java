package com.atlas.bank.atlas_bank.transaction.service.fee;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

//Es un componente ya que es una implementación con logica independiente
@Component              // CUENTA DE AHORRO
public class SavingsFeeCalculator implements FeeCalculator{

    @Override
    public boolean supports(String accountType) {
        return "SAVINGS".equals(accountType);
    }

    @Override       //Si tenemos una caja de ahorro, vamos a cobrar como comision el 1%
    public BigDecimal calculate(BigDecimal amount) {
        return amount.multiply(new BigDecimal("0.01"));
    }

}
