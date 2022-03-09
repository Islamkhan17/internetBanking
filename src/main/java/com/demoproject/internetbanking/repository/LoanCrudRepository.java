package com.demoproject.internetbanking.repository;


import com.demoproject.internetbanking.model.Client;
import com.demoproject.internetbanking.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface LoanCrudRepository extends JpaRepository<Loan, Integer> {

    @Modifying
    @Query("SELECT l FROM Loan l WHERE l.client.id=:clientId")
    List<Loan> get(@Param("clientId") int clientId);

}
