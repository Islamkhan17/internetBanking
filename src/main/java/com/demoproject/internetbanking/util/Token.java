package com.demoproject.internetbanking.util;

public class Token {
    private String token;

    public Token(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    public static String generateToken(String password, Long number) {
        int hash = number.hashCode();
        return cipher(password, hash);
    }

    private static String cipher(String message, int offset) {
        StringBuilder result = new StringBuilder();
        for (char character : message.toCharArray()) {
            if (character != ' ') {
                int originalAlphabetPosition = character - 'a';
                int newAlphabetPosition = (originalAlphabetPosition + offset) % 46;
                char newCharacter = (char) ('a' + newAlphabetPosition);
                result.append(newCharacter);
            } else {
                result.append(character);
            }
        }
        return String.valueOf(result);
    }

    public static void main(String[] args) {
        String token = Token.generateToken("12312safasdasd", 1234567l);
        String token1 = Token.generateToken("12312safasdasd", 1234567l);
        System.out.println(token.compareTo(token1));
        System.out.println("маша".compareTo("саша"));
    }
}
