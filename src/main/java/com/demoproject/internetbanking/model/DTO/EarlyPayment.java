package com.demoproject.internetbanking.model.DTO;

public class EarlyPayment {
    private Long earlyPayment;

    public EarlyPayment(Long earlyPayment) {
        this.earlyPayment = earlyPayment;
    }

    public Long getEarlyPayment() {
        return earlyPayment;
    }

    public void setEarlyPayment(Long earlyPayment) {
        this.earlyPayment = earlyPayment;
    }
}
