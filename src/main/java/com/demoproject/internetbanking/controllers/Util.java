package com.demoproject.internetbanking.controllers;

public class Util {
    public static void main(String[] args) {
        long fullCredit = 3000000 * (100 + 2) / 100;
        System.out.println("credit = " + fullCredit);
        System.out.println((long) (100 + 2) / 100);
        float debt = fullCredit - fullCredit/24*11;
        System.out.println(debt);
    }
}
