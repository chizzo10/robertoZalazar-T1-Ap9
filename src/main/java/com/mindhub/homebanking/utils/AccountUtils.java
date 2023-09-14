package com.mindhub.homebanking.utils;

import java.util.Random;

public class AccountUtils {

    public static String generateRandomAccountNumber() {
        Random random = new Random();
        return "VIN-" + random.nextInt(90000000);
    }
}
