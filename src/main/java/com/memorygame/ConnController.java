package com.memorygame;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

/**
 *  Controller for Connection.fxml
 *  Players choose which one they want to be (first or second) here
 *  Only in multiplayer mode
 */
public class ConnController {
    OpenWindow op = new OpenWindow();

    @FXML
    void back(ActionEvent event) throws IOException {
        op.showWindow(event, "NewGame.fxml", "New game");

    }

    @FXML
    void first(ActionEvent event) throws IOException {
        op.showWindow(event, "First.fxml", "First player");

    }

    @FXML
    void second(ActionEvent event) throws IOException {
        op.showWindow(event, "Second.fxml", "Second player");

    }

}

