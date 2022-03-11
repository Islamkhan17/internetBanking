package com.demoproject.internetbanking.controllers;

import com.demoproject.internetbanking.model.Client;
import com.demoproject.internetbanking.model.Loan;
import com.demoproject.internetbanking.repository.ClientRepository;
import com.demoproject.internetbanking.repository.LoanRepository;
import com.demoproject.internetbanking.model.DTO.Token;
import com.demoproject.internetbanking.util.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.Locale;

import static com.demoproject.internetbanking.util.ValidationUtil.checkNotFound;

@Controller
public class MainController {

    private static final Client CLIENT1 = new Client("Маша", "Иванова", "Петрова", 12345l, 7777777l, "пороль", "Москва", Token.generateToken("пороль", 7777771l));
    private static final Client CLIENT2 = new Client("Маша", "Иванова", "Петрова", 12345l, 7777777l, "пороль1", "Москва", Token.generateToken("пороль1", 7777772l));
    private static final Client CLIENT3 = new Client("Маша", "Иванова", "Петрова", 12345l, 7777777l, "пороль2", "Москва", Token.generateToken("пороль2", 7777773l));
    private static final Client CLIENT4 = new Client("Маша", "Иванова", "Петрова", 12345l, 7777777l, "пороль3", "Москва", Token.generateToken("пороль3", 7777774l));

    private ClientRepository clientRepository;
    private LoanRepository loanRepository;

    private void init() {
        clientRepository.save(CLIENT1);
        clientRepository.save(CLIENT2);
        clientRepository.save(CLIENT3);
        clientRepository.save(CLIENT4);
    }


    public MainController(ClientRepository clientRepository, LoanRepository loanRepository) {
        this.clientRepository = clientRepository;
        this.loanRepository = loanRepository;
//        init();
    }

    @GetMapping("/")
    public String homePage(Model model) {
        model.addAttribute("title", "InternetBanking");
        return "home";
    }

    @PostMapping("/register")
    public ResponseEntity<Token> registered(@RequestParam(value = "name", required = true) String name,
                                            @RequestParam(value = "lastName", required = true) String lastName,
                                            @RequestParam(value = "patronymic", required = false) String patronymic,
                                            @RequestParam(value = "iin", required = true) Long iin,
                                            @RequestParam(value = "phone", required = true) Long phone,
                                            @RequestParam(value = "password", required = true) String password,
                                            @RequestParam(value = "address", required = false) String address) {
        System.out.println("REGISTERED");

        String token = Token.generateToken(password, phone);
        Client client = new Client(name, lastName, patronymic, iin, phone, password, address, token);
        clientRepository.save(client);

        return new ResponseEntity<>(new Token(token), HttpStatus.OK);
    }

    // Генерирует токен по телефону и поролю. По токену ищет токен клиента в базе.
    // Если токен найден и токены равны между собой отправляет токен клиенту.
    @GetMapping("/register")
    public ResponseEntity<Token> getToken(@RequestParam("phone") Long phone, @RequestParam("password") String password) {
        System.out.println("GetToken".toUpperCase(Locale.ROOT));
        String bdToken;
        String token = Token.generateToken(password, phone);
        try {
            bdToken = checkNotFound(clientRepository.get(token), "not found").getToken();
            if (bdToken.compareTo(token) != 0) {
                throw new NotFoundException("not compare");
            }
        } catch (NotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(new Token(token), HttpStatus.OK);
    }


    @PostMapping("/application")
    public ResponseEntity<String> application(@RequestParam(value = "token", required = true) String token,
                                              @RequestParam(value = "loanAmount", required = false) Long loanAmount,
                                              @RequestParam(value = "loanPeriod", required = false) Integer loanPeriod,
                                              @RequestParam(value = "percent", required = false) Integer percent) {
        System.out.println("application".toUpperCase(Locale.ROOT));
        Client client;
        try {
            client = checkNotFound(clientRepository.get(token), "Check token");
            if (client.getToken().compareTo(token) != 0) {
                throw new RuntimeException("Token Not Compare");
            }
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
        Long credit = loanAmount * (100 + percent) / 100;
        Long monthlyPayment = credit / loanPeriod;
        Loan loan = new Loan(loanAmount, loanPeriod, percent, client, credit, monthlyPayment);
        loanRepository.save(loan);

        return new ResponseEntity<>("success", HttpStatus.OK);
    }

}