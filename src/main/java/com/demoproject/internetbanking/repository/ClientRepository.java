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

    public boolean delete(int id) {
//        return crudRepository.delete(id) != 0;
        return false;
    }


    public Client get(int id) {
        return clientCrudRepository.findById(id).orElse(null);
    }

    public List<Client> getAll() {
        return clientCrudRepository.findAll();
    }

}
