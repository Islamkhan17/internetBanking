package com.demoproject.internetbanking.repository;

import com.demoproject.internetbanking.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface ClientCrudRepository extends JpaRepository<Client, Integer> {
}
