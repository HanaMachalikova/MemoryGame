package com.memorygame;

import java.util.HashMap;

public class Multiton {
    private static final HashMap<String, Multiton> instances = new HashMap<>();
    private String message;

    private Multiton(String message) {
        this.message = message;
    }

    public static synchronized Multiton getInstance(String key, String message) {
        if (!instances.containsKey(key)) {
            instances.put(key, new Multiton(message));
        }
        return instances.get(key);
    }

    public String getMessage() {
        return this.message;
    }
}
