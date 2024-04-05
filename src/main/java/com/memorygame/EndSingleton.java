package com.memorygame;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class EndSingleton {
    private static EndSingleton single_instance = null;

    public Label text_enter;
    public Label invalid1;
    public Label invalid2;
    public TextField code_field;
    public Button next_level;
    public Label y_win;
    public Label y_lose;
    public Label o_win;
    public Label o_lose;

    public Label you;
    public Label opponent;
    public Label result;
    public Label no_previous;
    public Button new_game;
    public Label game_over;

    public static EndSingleton getInstance() {
        if (single_instance == null)
            single_instance = new EndSingleton();

        return single_instance;
    }

    public Label getText_enter() {
        return text_enter;
    }

    public Label getInvalid1() {
        return invalid1;
    }

    public Label getInvalid2() {
        return invalid2;
    }

    public TextField getCode_field() {
        return code_field;
    }

    public Button getNext_level() {
        return next_level;
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

    public Label getYou() {
        return you;
    }

    public Label getOpponent() {
        return opponent;
    }

    public Label getResult() {
        return result;
    }

    public Label getNo_previous() {
        return no_previous;
    }

    public Button getNew_game() {
        return new_game;
    }

    public Label getGame_over() {
        return game_over;
    }

    public void setText_enter(Label text_enter) {
        this.text_enter = text_enter;
    }

    public void setInvalid1(Label invalid1) {
        this.invalid1 = invalid1;
    }

    public void setInvalid2(Label invalid2) {
        this.invalid2 = invalid2;
    }

    public void setCode_field(TextField code_field) {
        this.code_field = code_field;
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

    public void setResult(Label result) {
        this.result = result;
    }

    public void setNo_previous(Label no_previous) {
        this.no_previous = no_previous;
    }

    public void setNew_game(Button new_game) {
        this.new_game = new_game;
    }

    public void setGame_over(Label game_over) {
        this.game_over = game_over;
    }

}