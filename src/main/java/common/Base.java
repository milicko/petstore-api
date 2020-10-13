package common;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;

public class Base {

    public static String createRandomString(int length) {
        return RandomStringUtils.randomAlphabetic(length);
    }

    public static int createRandomNumber(int upperbound) {
        Random rand = new Random();
        return rand.nextInt(upperbound);
    }

}
