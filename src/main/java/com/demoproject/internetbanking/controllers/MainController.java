package com.demoproject.internetbanking.controllers;

import antlr.Token;
import com.demoproject.internetbanking.model.Client;
import com.demoproject.internetbanking.model.Loan;
import com.demoproject.internetbanking.repository.ClientRepository;
import com.demoproject.internetbanking.repository.LoanRepository;
import com.demoproject.internetbanking.util.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.demoproject.internetbanking.util.ValidationUtil.checkNotFoundWithId;

@Controller
public class MainController {

    private static final Client CLIENT = new Client("Маша", "Иванова", "Петрова", 1231231, 7777777, "пороль", "Москва");

    private ClientRepository clientRepository;
    private LoanRepository loanRepository;


    public MainController(ClientRepository clientRepository, LoanRepository loanRepository) {
        this.clientRepository = clientRepository;
        this.loanRepository = loanRepository;
    }

    @GetMapping("/")
    public String homePage(Model model) {
        model.addAttribute("title", "Главная страница");
        return "home";
    }

    @PostMapping("/register")
    public ResponseEntity<Token> registered(@RequestParam(value = "name", required = false) String name,
                                            @RequestParam(value = "lastName", required = false) String lastName,
                                            @RequestParam(value = "patronymic", required = false) String patronymic,
                                            @RequestParam(value = "iin", required = false) Integer iin,
                                            @RequestParam(value = "phone", required = false) Integer phone,
                                            @RequestParam(value = "password", required = false) String password,
                                            @RequestParam(value = "address", required = false) String address,
                                            Model model) {
        System.out.println("REGISTERED");
        Client client = new Client(name, lastName, patronymic, iin, phone, password, address);
        Token token = new Token(client.getPhone(), client.getPassword());
        System.out.println(token);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @GetMapping("/register")
    public ResponseEntity<Token> getToken(@RequestParam("phone") Integer phone, @RequestParam("password") String password) {
        System.out.println("GetToken");
        //изменить если надо сделать запрос в базу на основании телефона и пороля
        System.out.println(phone);
        System.out.println(password);
        Token token = new Token(phone, password);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }


    // надо сгенерировать токен и вернуть его
    // генерировать надо внутри класса Client и записывать в бузу не известно
    @PostMapping("/application")
    public ResponseEntity<String> application(@RequestParam(value = "token", required = true) String token,
                                              @RequestParam(value = "loanAmount", required = false) Long loanAmount,
                                              @RequestParam(value = "loanPeriod", required = false) Integer loanPeriod,
                                              @RequestParam(value = "percent", required = false) Integer percent,
                                              Model model) {
        System.out.println("application".toUpperCase(Locale.ROOT));
        System.out.println(token);

        Client client;
        try {
            client = checkNotFoundWithId(clientRepository.get(10), 10);
        } catch (NotFoundException e) {
            return new ResponseEntity<String>("failed", HttpStatus.UNAUTHORIZED);
        }
        Loan loan = new Loan(loanAmount, loanPeriod, percent);
        loan.setClient(client);
        loanRepository.save(loan);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }


    @GetMapping("/client/{id}/nextPaymentDate")
    public ResponseEntity<LocalDate> nextPaymentDate(@PathVariable("id") Integer id) {
        System.out.println("nextPaymentDate".toUpperCase(Locale.ROOT));
        Loan loan;
        try {
            loan = checkNotFoundWithId(loanRepository.get(id), id);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        LocalDate localDate = LocalDate.of(2022, 3, 5);
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

        return new ResponseEntity<>(nextPaymentDate, HttpStatus.OK);
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