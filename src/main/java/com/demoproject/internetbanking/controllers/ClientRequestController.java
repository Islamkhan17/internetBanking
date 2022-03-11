package com.demoproject.internetbanking.controllers;

import com.demoproject.internetbanking.model.DTO.Debt;
import com.demoproject.internetbanking.model.DTO.EarlyPayment;
import com.demoproject.internetbanking.model.DTO.NextPayment;
import com.demoproject.internetbanking.model.DTO.PaymentCount;
import com.demoproject.internetbanking.model.Loan;
import com.demoproject.internetbanking.repository.ClientRepository;
import com.demoproject.internetbanking.repository.LoanRepository;
import com.demoproject.internetbanking.model.DTO.Date;
import com.demoproject.internetbanking.util.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

import static com.demoproject.internetbanking.util.CalculationUtil.*;
import static com.demoproject.internetbanking.util.ValidationUtil.checkNotFoundWithId;

@Controller
public class ClientRequestController {


    private LoanRepository loanRepository;

    public ClientRequestController(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    @GetMapping("/client/{id}/debt")
    public ResponseEntity<Debt> debt(@PathVariable("id") Integer id) {
        System.out.println("DEBT");
        Loan loan;
        try {
            loan = checkNotFoundWithId(loanRepository.get(id), id);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
                LocalDate localDate = LocalDate.now();
//        LocalDate localDate = LocalDate.of(2024, 3, 12);
        LocalDate registerDate = LocalDate.ofInstant(loan.getRegistered().toInstant(), ZoneId.systemDefault());
        if (localDate.compareTo(registerDate) < 0)
        {
            System.out.println("Current date is less than register date");
            return new ResponseEntity<>(null, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        LocalDate localMonth = localDate;
        if (isDayBeforePaidDay(registerDate, localDate)) {
            localMonth = localDate.minus(1, ChronoUnit.MONTHS);
        }
        int paidMonths = paidMonthCalculate(registerDate, localMonth);

        if (isPaidPeriodIsEnded(loan.getLoanPeriod(), paidMonths)) {
            System.out.println("Loan period is ended. Nothing to debt");
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        Long debt = loan.getCredit() - loan.getMonthlyPayment() * paidMonths;
        return new ResponseEntity<>(new Debt(debt), HttpStatus.OK);
    }


    // не забыть поменять localDate!
    @GetMapping("/client/{id}/paymentCount")
    public ResponseEntity<PaymentCount> paymentCount(@PathVariable("id") Integer id) {
        System.out.println("paymentCount".toUpperCase(Locale.ROOT));
        Loan loan;
        try {
            loan = checkNotFoundWithId(loanRepository.get(id), id);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
                LocalDate localDate = LocalDate.now();
//        LocalDate localDate = LocalDate.of(2023, 4, 10);
        LocalDate registerDate = LocalDate.ofInstant(
                loan.getRegistered().toInstant(), ZoneId.systemDefault());
        if (localDate.compareTo(registerDate) < 0)
        {
            System.out.println("Current date is less than register date");
            return new ResponseEntity<>(null, HttpStatus.UNPROCESSABLE_ENTITY);
        }

        LocalDate localMonth = localDate;
        if (isDayBeforePaidDay(registerDate, localDate)) {
            localMonth = localDate.minus(1, ChronoUnit.MONTHS);
        }
        int paidMonths = paidMonthCalculate(registerDate, localMonth);
        System.out.println("paidMonths = "+paidMonths);
        if (isPaidPeriodIsEnded(loan.getLoanPeriod(), paidMonths)) {
            System.out.println("Loan period is ended.");
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        int paymentCount = loan.getLoanPeriod() - paidMonths;
        return new ResponseEntity<>(new PaymentCount(paymentCount), HttpStatus.OK);
    }


    @GetMapping("/client/{id}/nextPayment")
    public ResponseEntity<NextPayment> nextPayment(@PathVariable("id") Integer id) {
        System.out.println("nextPayment".toUpperCase(Locale.ROOT));
        Loan loan;
        try {
            loan = checkNotFoundWithId(loanRepository.get(id), id);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
                LocalDate localDate = LocalDate.now();
//        LocalDate localDate = LocalDate.of(2024, 4, 10);
        LocalDate registerDate = LocalDate.ofInstant(
                loan.getRegistered().toInstant(), ZoneId.systemDefault());
        if (localDate.compareTo(registerDate) < 0)
        {
            System.out.println("Current date is less than register date");
            return new ResponseEntity<>(null, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        LocalDate localMonth = localDate;
        if (isDayBeforePaidDay(registerDate, localDate)) {
            localMonth = localDate.minus(1, ChronoUnit.MONTHS);
        }
        int paidMonths = paidMonthCalculate(registerDate, localMonth);
        if (isPaidPeriodIsEnded(loan.getLoanPeriod(), paidMonths)) {
            System.out.println("Loan period is ended.");
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        return new ResponseEntity<>(new NextPayment(loan.getMonthlyPayment()), HttpStatus.OK);
    }

    @GetMapping("/client/{id}/earlyPayment")
    public ResponseEntity<EarlyPayment> earlyPayment(@PathVariable("id") Integer id) {
        System.out.println("earlyPayment".toUpperCase(Locale.ROOT));
        Loan loan;
        try {
            loan = checkNotFoundWithId(loanRepository.get(id), id);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        LocalDate localDate = LocalDate.now();
//        LocalDate localDate = LocalDate.of(2024, 3, 12);
        LocalDate registerDate = LocalDate.ofInstant(loan.getRegistered().toInstant(), ZoneId.systemDefault());

        if (localDate.compareTo(registerDate) < 0)
        {
            System.out.println("Current date is less than register date");
            return new ResponseEntity<>(null, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        System.out.println(localDate);
        System.out.println(registerDate);
        LocalDate localMonth = localDate;

        if (isDayBeforePaidDay(registerDate, localDate)) {
            localMonth = localDate.minus(1, ChronoUnit.MONTHS);
        }

        int paidMonths = paidMonthCalculate(registerDate, localMonth);

        if (isPaidPeriodIsEnded(loan.getLoanPeriod(), paidMonths)) {
            System.out.println("Loan period is ended. Nothing to debt");
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        Long debt = loan.getCredit() - loan.getCredit() / loan.getLoanPeriod() * paidMonths;
        Long earlyPayment = debt * paidMonths / loan.getLoanPeriod() * (100 + loan.getPercent()) / 100;
        return new ResponseEntity<>(new EarlyPayment(earlyPayment), HttpStatus.OK);
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
//        LocalDate localDate = LocalDate.of(2024, 3, 12);
        LocalDate registerDate = LocalDate.ofInstant(
                loan.getRegistered().toInstant(), ZoneId.systemDefault());
        if (localDate.compareTo(registerDate) < 0)
        {
            System.out.println("Current date is less than register date");
            return new ResponseEntity<>(null, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        LocalDate nextPaymentDate = registerDate.plus(1, ChronoUnit.MONTHS);
        nextPaymentDate = nextPaymentDate.plus(yearOdds(registerDate, localDate), ChronoUnit.YEARS);

        LocalDate localMonth = localDate;
        if (isDayBeforePaidDay(registerDate, localDate)) {
            nextPaymentDate = nextPaymentDate.minus(1, ChronoUnit.MONTHS);
            localMonth = localDate.minus(1, ChronoUnit.MONTHS);
        }
        int paidMonths = paidMonthCalculate(registerDate, localMonth);
        if (isPaidPeriodIsEnded(loan.getLoanPeriod(), paidMonths)) {
            System.out.println("Loan period is ended.");
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        return new ResponseEntity<>(new Date(nextPaymentDate), HttpStatus.OK);
    }



}
