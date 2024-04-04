package com.memorygame;

import java.util.HashMap;

public class ResultMultiton {
    private static final HashMap<String, ResultMultiton> instances = new HashMap<>();
    private boolean failed;
    private long time;

    public ResultMultiton(boolean failed, long time) {
        this.failed = failed;
        this.time = time;
    }

    public static synchronized ResultMultiton getInstance(String key) {
        if (!instances.containsKey(key)) {
            instances.put(key, new ResultMultiton(false, 0));
        }
        return instances.get(key);
    }

    public boolean isFailed() {
        return failed;
    }

    public long getTime() {
        return time;
    }

    public void setFailed(boolean failed) {
        this.failed = failed;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
