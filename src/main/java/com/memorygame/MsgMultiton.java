package com.memorygame;

import java.util.HashMap;

public class MsgMultiton {
    private static final HashMap<String, MsgMultiton> instances = new HashMap<>();
    private String message;

    private MsgMultiton(String message) {
        this.message = message;
    }

    public static synchronized MsgMultiton getInstance(String key) {
        if (!instances.containsKey(key)) {
            instances.put(key, new MsgMultiton(""));
        }
        return instances.get(key);
    }

    public synchronized String getMessage() {

        return this.message;
    }

    public void setMessage(String message) {

        this.message = message;
    }
}
