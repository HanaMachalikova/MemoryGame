package com.memorygame;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import java.io.IOException;

/**
 * Controller for NewGame.fxml where player decides which mode he wants to play
 */
public class NewGameController {
    @FXML
    void multi_game(ActionEvent event) throws IOException {
        OpenWindow ow = new OpenWindow();
        ow.showWindow(event, "Connection.fxml", "Connection");

    }

    @FXML
    void single_game(ActionEvent event) throws IOException {
        OpenWindow ow = new OpenWindow();
        ow.showWindow(event, "Single.fxml", "Single game");
        //showWindow(event, "Single.fxml", "Single game");
    }

    @FXML
    void back(ActionEvent event) throws IOException {
        OpenWindow ow = new OpenWindow();
        ow.showWindow(event, "Home.fxml", "Home");

    }
}
