package com.demoproject.internetbanking.model.DTO;

public class NextPayment {
    private Long nextPayment;

    public NextPayment(Long nextPayment) {
        this.nextPayment = nextPayment;
    }

    public Long getNextPayment() {
        return nextPayment;
    }

    public void setNextPayment(Long nextPayment) {
        this.nextPayment = nextPayment;
    }
}
