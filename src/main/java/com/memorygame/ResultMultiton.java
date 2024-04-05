package com.memorygame;

import java.util.HashMap;

/**
 * Class using multiton for accessing variables for other classes
 * These variables refer to result finished level in multiplayer mode
 * Key in the HashMap is level of which results are wanted
 */
public class ResultMultiton {
    private static final HashMap<String, ResultMultiton> instances = new HashMap<>();
    private boolean failed;
    private long time;

    public ResultMultiton(boolean failed, int time) {
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
