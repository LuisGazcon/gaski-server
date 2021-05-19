package com.gazcon.gaski.global.utils;

public interface UserUtils {
    public static String formatName(String name) {
        var lowered = name.toLowerCase();
        String[] splitted = lowered.split(" ");
        String capitalized = new String();
        for(int i = 0; i < splitted.length; i++) {
             String.join(
                 "",
                capitalized,
                " ",
                splitted[i].substring(0, 1).toUpperCase(),
                splitted[i].substring(1)
            );
        }
        return capitalized;
    }

    public static String formatEmail(String email) {
        return email.toLowerCase().trim();
    }

    public static String formatUsername(String username) {
        return username.toLowerCase().trim();
    }

    public static String getKeywords(String firstName, String lastName, String username) {
        return null;
    }
}
