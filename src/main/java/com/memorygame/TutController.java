package com.memorygame;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

/**
 * Controller for Tutorial.fxml where is written how to play Typing Game
 */
public class TutController {
    OpenWindow op = new OpenWindow();
    @FXML
    void go_back(ActionEvent event) throws IOException {
        op.showWindow(event, "Home.fxml", "Typing Game");
    }
}
