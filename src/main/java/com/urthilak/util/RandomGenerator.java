package com.urthilak.util;


import java.security.SecureRandom;

public class RandomGenerator {

    private static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final String AB_CAPS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final static String[] STATES = new String[]{"STATE-1", "STATE-2", "STATE-3", "STATE-4", "STATE-5", "STATE-6"};
    private static SecureRandom random = new SecureRandom();

    public static String getRandomState() {

        int nextRandom = random.nextInt(5);

        return STATES[nextRandom];
    }

    public static String getRandomString(int len) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(AB.charAt(random.nextInt(AB.length())));
        return sb.toString();
    }

    public static String getRandomStringCaps(int len) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(AB_CAPS.charAt(random.nextInt(AB_CAPS.length())));
        return sb.toString();
    }
}
