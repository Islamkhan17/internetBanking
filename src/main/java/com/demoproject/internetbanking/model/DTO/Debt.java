package com.demoproject.internetbanking.model.DTO;

public class Debt {
    private Long debt;

    public Debt(Long debt) {
        this.debt = debt;
    }

    public Long getDebt() {
        return debt;
    }

    public void setDebt(Long debt) {
        this.debt = debt;
    }
}
