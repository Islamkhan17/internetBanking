package com.demoproject.internetbanking.util;

import java.time.LocalDate;

public class CalculationUtil {
    private CalculationUtil() {
    }

    public static int paidMonthCalculate(LocalDate registerDate, LocalDate dateNow) {
        return (dateNow.getYear() - registerDate.getYear()) * 12 - registerDate.getMonthValue() + dateNow.getMonthValue();
    }

    public static boolean isDayBeforePaidDay(LocalDate registerDate, LocalDate dateNow) {
        return dateNow.getDayOfMonth() < registerDate.getDayOfMonth();
    }

    public static boolean isPaidPeriodIsEnded(int loanPeriod, int paidMonths) {
        return loanPeriod - paidMonths <= 0;
    }

    public static int yearOdds(LocalDate registerDate, LocalDate dateNow) {
        return dateNow.getYear() - registerDate.getYear();
    }

    public static void main(String[] args) {
        LocalDate localDate1 = LocalDate.of(2022, 4, 10);
        LocalDate localDate2 = LocalDate.now();
        System.out.println(paidMonthCalculate(localDate2,localDate1));
    }

}
