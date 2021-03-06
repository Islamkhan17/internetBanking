package com.demoproject.internetbanking.repository;

import com.demoproject.internetbanking.model.Client;
import com.demoproject.internetbanking.model.Loan;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
public class LoanRepository {
    private final LoanCrudRepository loanCrudRepository;

    public LoanRepository(LoanCrudRepository loanCrudRepository) {
        this.loanCrudRepository = loanCrudRepository;
    }

    public Loan save(Loan loan){
        return loanCrudRepository.save(loan);
    }

    public Loan get(int clientId){
        return loanCrudRepository.get(clientId).stream().findAny().orElse(null);
    }
    public Loan get(int id, int clientId) {
        Loan loan = loanCrudRepository.findById(id).orElse(null);
        return loan != null && loan.getClient().getId() == clientId ? loan : null;
    }
}
