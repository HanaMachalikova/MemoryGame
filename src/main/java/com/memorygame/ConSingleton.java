package com.memorygame;

import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ConSingleton {
    private static ConSingleton single_instance = null;

    public Button next_level;
    public Label y_win;
    public Label y_lose;
    public Label o_win;
    public Label o_lose;

    public Label you;
    public Label opponent;
    public Label result;
    public Label no_previous;
    public boolean ready = false;

    public static ConSingleton getInstance() {
        if (single_instance == null)
            single_instance = new ConSingleton();

        return single_instance;
    }

    public void setNo_previous(Label no_previous) {
        this.no_previous = no_previous;
    }

    public void setNext_level(Button next_level) {
        this.next_level = next_level;
    }

    public void setY_win(Label y_win) {
        this.y_win = y_win;
    }

    public void setY_lose(Label y_lose) {
        this.y_lose = y_lose;
    }

    public void setO_win(Label o_win) {
        this.o_win = o_win;
    }

    public void setO_lose(Label o_lose) {
        this.o_lose = o_lose;
    }

    public void setYou(Label you) {
        this.you = you;
    }

    public void setOpponent(Label opponent) {
        this.opponent = opponent;
    }

    public Label getResult() {
        return result;
    }

    public void setResult(Label result) {
        this.result = result;
    }


    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public Button getNext_level() {
        return next_level;
    }

    public Label getYou() {
        return you;
    }

    public Label getOpponent() {
        return opponent;
    }

    public Label getY_win() {
        return y_win;
    }

    public Label getY_lose() {
        return y_lose;
    }

    public Label getO_win() {
        return o_win;
    }

    public Label getO_lose() {
        return o_lose;
    }

    public Label getNo_previous() {
        return no_previous;
    }
}