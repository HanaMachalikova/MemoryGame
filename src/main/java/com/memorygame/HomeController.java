package com.memorygame;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class HomeController {

    OpenWindow op = new OpenWindow();


    @FXML
    void how_to_play(ActionEvent event) throws IOException {
        op.showWindow(event, "Tutorial.fxml", "Tutorial");
    }

    @FXML
    void new_game(ActionEvent event) throws IOException {
        op.showWindow(event, "NewGame.fxml", "New Game");
    }

}
