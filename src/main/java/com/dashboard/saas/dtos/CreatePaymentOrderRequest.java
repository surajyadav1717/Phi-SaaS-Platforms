package com.dashboard.saas.dtos;

import java.math.BigDecimal;

public class CreatePaymentOrderRequest {

    private BigDecimal amount;


    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
