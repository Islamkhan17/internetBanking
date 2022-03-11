package com.demoproject.internetbanking.util;

import java.time.LocalDate;

public class CalculationUtil {
    private CalculationUtil() {
    }

    public static int paidMonthCalculate(LocalDate registerDate, LocalDate dateNow) {
        return (dateNow.getYear() - registerDate.getYear()) * 12 - registerDate.getMonthValue() + dateNow.getMonthValue() - 1;
    }

    public static boolean isDayBeforePaidDay(LocalDate registerDate, LocalDate dateNow) {
        return dateNow.getDayOfMonth() < registerDate.getDayOfMonth();
    }

    public static boolean isPaidPeriodIsEnded(int loanPeriod, int paidMonths) {
        return loanPeriod - paidMonths <= 0;
    }

    public static int yearOdds(LocalDate registerDate, LocalDate dateNow) {
        System.out.println(dateNow.getYear());
        System.out.println(registerDate.getYear());
        return dateNow.getYear() - registerDate.getYear();
    }

}
