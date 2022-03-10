package com.demoproject.internetbanking.model;

import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "loans")
public class Loan extends BaseEntity{

    private Long loanAmount;
    // исчисляется в месяцах
    private Integer loanPeriod;
    @Range(min = 1, max = 100)
    private Integer percent;
    @Column(name = "registered", nullable = false, columnDefinition = "timestamp default now()", updatable = false)
    private Date registered = new Date();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    public Loan(Long loanAmount, Integer loanPeriod, Integer percent, Client client) {
        super(null);
        this.loanAmount = loanAmount;
        this.loanPeriod = loanPeriod;
        this.percent = percent;
        this.client = client;
        this.registered = new Date();
    }

    public Loan() {
    }

    public Long getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(Long loanAmount) {
        this.loanAmount = loanAmount;
    }

    public Integer getLoanPeriod() {
        return loanPeriod;
    }

    public void setLoanPeriod(Integer loanPeriod) {
        this.loanPeriod = loanPeriod;
    }

    public Integer getPercent() {
        return percent;
    }

    public void setPercent(Integer percent) {
        this.percent = percent;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Date getRegistered() {
        return registered;
    }

    public void setRegistered(Date registered) {
        this.registered = registered;
    }
}
