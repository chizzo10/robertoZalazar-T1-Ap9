package com.mindhub.homebanking.utils;

import java.util.Random;

public class CardUtils {

    public static String generateRandomCardNumber() {
        Random random = new Random();
        StringBuilder cardNumber = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            cardNumber.append(String.format("%04d", random.nextInt(10000)));
            if (i < 3) {
                cardNumber.append(" ");
            }
        }
        return cardNumber.toString();
    }

    public static int generateRandomCvvNumber() {
        Random random = new Random();
        return random.nextInt(1000);
    }
}
