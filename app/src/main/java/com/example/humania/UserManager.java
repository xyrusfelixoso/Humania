package com.example.humania;

import java.util.HashMap;
import java.util.Map;

public class UserManager {
    private static Map<String, String> users = new HashMap<>();

    static {
        // Default users
        users.put("pasikat", "sijerry");
        users.put("admin", "admin123");
        users.put("user", "user123");
        users.put("org", "org123");
    }

    public static boolean validateUser(String email, String password) {
        return users.containsKey(email) && users.get(email).equals(password);
    }

    public static boolean registerUser(String email, String password) {
        if (users.containsKey(email)) {
            return false;
        }
        users.put(email, password);
        return true;
    }
}
