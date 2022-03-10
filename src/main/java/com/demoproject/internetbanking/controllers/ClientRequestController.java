package com.demoproject.internetbanking.controllers;

import com.demoproject.internetbanking.repository.ClientRepository;
import com.demoproject.internetbanking.repository.LoanRepository;
import org.springframework.stereotype.Controller;

@Controller
public class ClientRequestController {

    private ClientRepository clientRepository;
    private LoanRepository loanRepository;

    public ClientRequestController(ClientRepository clientRepository, LoanRepository loanRepository) {
        this.clientRepository = clientRepository;
        this.loanRepository = loanRepository;
//        init();
    }

}
