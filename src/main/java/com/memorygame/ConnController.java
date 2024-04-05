package com.memorygame;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

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

