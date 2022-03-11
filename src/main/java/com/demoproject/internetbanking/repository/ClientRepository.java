package com.demoproject.internetbanking.repository;

import com.demoproject.internetbanking.model.Client;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ClientRepository {

    private final ClientCrudRepository clientCrudRepository;

    public ClientRepository(ClientCrudRepository clientCrudRepository) {
        this.clientCrudRepository = clientCrudRepository;
    }

    public Client save(Client client){
        return clientCrudRepository.save(client);
    }


    public Client get(int id) {
        return clientCrudRepository.findById(id).orElse(null);
    }

    public Client get(String token){
        return clientCrudRepository.getByToken(token).stream().findAny().orElse(null);
    }


    public List<Client> getAll() {
        return clientCrudRepository.findAll();
    }

}
