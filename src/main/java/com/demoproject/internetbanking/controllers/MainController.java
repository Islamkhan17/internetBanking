package com.demoproject.internetbanking.controllers;

import com.demoproject.internetbanking.model.Client;
import com.demoproject.internetbanking.util.Date;
import com.demoproject.internetbanking.model.Loan;
import com.demoproject.internetbanking.repository.ClientRepository;
import com.demoproject.internetbanking.repository.LoanRepository;
import com.demoproject.internetbanking.util.Token;
import com.demoproject.internetbanking.util.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Locale;

import static com.demoproject.internetbanking.util.ValidationUtil.checkNotFound;
import static com.demoproject.internetbanking.util.ValidationUtil.checkNotFoundWithId;

@Controller
public class MainController {

    private static final Client CLIENT1 = new Client("Маша", "Иванова", "Петрова", 12345l, 7777777l, "пороль", "Москва", Token.generateToken("пороль",7777771l ));
    private static final Client CLIENT2 = new Client("Маша", "Иванова", "Петрова", 12345l, 7777777l, "пороль1", "Москва", Token.generateToken("пороль1",7777772l ));
    private static final Client CLIENT3 = new Client("Маша", "Иванова", "Петрова", 12345l, 7777777l, "пороль2", "Москва", Token.generateToken("пороль2",7777773l ));
    private static final Client CLIENT4 = new Client("Маша", "Иванова", "Петрова", 12345l, 7777777l, "пороль3", "Москва", Token.generateToken("пороль3",7777774l ));

    private ClientRepository clientRepository;
    private LoanRepository loanRepository;

    private void init(){
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


    // генерировать надо внутри класса Client и записывать в бузу не известно
    @PostMapping("/application")
    public ResponseEntity<String> application(@RequestParam(value = "token", required = true) String token,
                                              @RequestParam(value = "loanAmount", required = false) Long loanAmount,
                                              @RequestParam(value = "loanPeriod", required = false) Integer loanPeriod,
                                              @RequestParam(value = "percent", required = false) Integer percent) {
        System.out.println("application".toUpperCase(Locale.ROOT));
        Client client;
        try {
            client = checkNotFound(clientRepository.get(token),"Check token");
            if (client.getToken().compareTo(token)!= 0){
                throw new NotFoundException("Token Not Compare");
            }
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
        Loan loan = new Loan(loanAmount, loanPeriod, percent,client);
        loanRepository.save(loan);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }


    @GetMapping("/client/{id}/nextPaymentDate")
    public ResponseEntity<Date> nextPaymentDate(@PathVariable("id") Integer id) {
        System.out.println("nextPaymentDate".toUpperCase(Locale.ROOT));
        Loan loan;
        try {
            loan = checkNotFoundWithId(loanRepository.get(id), id);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        LocalDate localDate = LocalDate.now();
        LocalDate registerDate = LocalDate.ofInstant(
                loan.getRegistered().toInstant(), ZoneId.systemDefault());
        System.out.println(localDate);
        System.out.println(registerDate);
        LocalDate nextPaymentDate = registerDate;
        if (localDate.getDayOfMonth() > registerDate.getDayOfMonth()) {
            nextPaymentDate = registerDate.plus(1, ChronoUnit.MONTHS);
            System.out.println("дата следующей оплаты " + nextPaymentDate);
        } else {
            System.out.println("дата следующей оплаты " + nextPaymentDate);
        }


        return new ResponseEntity<>(new Date(nextPaymentDate), HttpStatus.OK);
    }


    @GetMapping("/client/{id}/debt")
    public ResponseEntity<Long> debt(@PathVariable("id") Integer id) {
        System.out.println("DEBT");
        Loan loan;
        try {
            loan = checkNotFoundWithId(loanRepository.get(id), id);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        LocalDate localDate = LocalDate.of(2023, 3, 5);
        LocalDate registerDate = LocalDate.ofInstant(
                loan.getRegistered().toInstant(), ZoneId.systemDefault());
        System.out.println(localDate);
        System.out.println(registerDate);
        System.out.println("local paidMonths before = " + localDate.getMonthValue());
        LocalDate localMonth = localDate;
        if (localDate.getDayOfMonth() < registerDate.getDayOfMonth()) {
            localMonth = localDate.minus(1, ChronoUnit.MONTHS);
            System.out.println("local paidMonths after = " + localDate.getMonthValue());
        }
        int paidMonths = (localMonth.getYear() - registerDate.getYear()) * 12 - registerDate.getMonthValue() + localMonth.getMonthValue();
        System.out.println("paidMonths = " + paidMonths);

        Long fullCredit = loan.getLoanAmount() * (100 + loan.getPercent()) / 100;
        System.out.println("credit = " + fullCredit);
        Long debt = fullCredit - fullCredit / loan.getLoanPeriod() * paidMonths;
        return new ResponseEntity<>(debt, HttpStatus.OK);
    }

    @GetMapping("/client/{id}/paymentCount")
    public ResponseEntity<Integer> paymentCount(@PathVariable("id") Integer id) {
        System.out.println("paymentCount".toUpperCase(Locale.ROOT));
        Loan loan;
        try {
            loan = checkNotFoundWithId(loanRepository.get(id), id);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        LocalDate localDate = LocalDate.of(2023, 3, 5);
        LocalDate registerDate = LocalDate.ofInstant(
                loan.getRegistered().toInstant(), ZoneId.systemDefault());
        System.out.println(localDate);
        System.out.println(registerDate);
        System.out.println("local paidMonths before = " + localDate.getMonthValue());
        LocalDate localMonth = localDate;
        if (localDate.getDayOfMonth() < registerDate.getDayOfMonth()) {
            localMonth = localDate.minus(1, ChronoUnit.MONTHS);
            System.out.println("local paidMonths after = " + localDate.getMonthValue());
        }
        int paidMonths = (localMonth.getYear() - registerDate.getYear()) * 12 - registerDate.getMonthValue() + localMonth.getMonthValue();
        System.out.println("paidMonths = " + paidMonths);

        Integer paymentCount = loan.getLoanPeriod() - paidMonths;
        return new ResponseEntity<>(paymentCount, HttpStatus.OK);
    }


    @GetMapping("/getAll")
    public ResponseEntity<List<Client>> getAll(Model model) {
        List<Client> clients = clientRepository.getAll();
        model.addAttribute("clients", clients);
        return new ResponseEntity<List<Client>>(clients, HttpStatus.OK);
    }

}