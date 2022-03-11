package com.demoproject.internetbanking.model.DTO;

public class PaymentCount {
    private Integer paymentCount;

    public PaymentCount(Integer paymentCount) {
        this.paymentCount = paymentCount;
    }

    public Integer getPaymentCount() {
        return paymentCount;
    }

    public void setPaymentCount(Integer paymentCount) {
        this.paymentCount = paymentCount;
    }
}
