package com.demoproject.internetbanking.repository;

import com.demoproject.internetbanking.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface ClientCrudRepository extends JpaRepository<Client, Integer> {

    @Modifying
    @Query("SELECT c FROM Client c WHERE c.token=:token")
    List<Client> getByToken(@Param("token") String token);
}
