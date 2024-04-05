package com.memorygame;

import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ConSingleton {
    private static ConSingleton single_instance = null;

    public Button next_level;

    public boolean you;
    public boolean opponent;
    public long time;
    public Label result;
    public boolean ready = false;

    public static ConSingleton getInstance() {
        if (single_instance == null)
            single_instance = new ConSingleton();

        return single_instance;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void setNext_level(Button next_level) {
        this.next_level = next_level;
    }

    public void setYou(boolean you) {
        this.you = you;
    }

    public void setOpponent(boolean opponent) {
        this.opponent = opponent;
    }

    public long getTime() {
        return time;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public Button getNext_level() {
        return next_level;
    }

    public boolean isYou() {
        return you;
    }

    public boolean isOpponent() {
        return opponent;
    }

    public boolean isReady() {
        return ready;
    }
}