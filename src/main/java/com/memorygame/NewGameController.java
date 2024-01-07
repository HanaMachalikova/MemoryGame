package com.memorygame;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;


public class NewGameController {
    @FXML
    private HBox mody;

    @FXML
    private Button multiplayer;

    @FXML
    private Label new_game_label;

    @FXML
    private Button singleplayer;

    @FXML
    private Button back;


    @FXML
    void multi_game(ActionEvent event) {

    }

    @FXML
    void single_game(ActionEvent event) throws IOException {
        Game.newWindow(event, "Single.fxml", "Single game");

    }

    @FXML
    void back(ActionEvent event) throws IOException {
        Game.newWindow(event, "Home.fxml", "Home");
    }

}
