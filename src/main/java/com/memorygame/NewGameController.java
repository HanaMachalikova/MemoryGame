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
    void multi_game(ActionEvent event) throws IOException {
        showWindow(event, "Connection.fxml", "Connection");

    }

    @FXML
    void single_game(ActionEvent event) throws IOException {
        showWindow(event, "Single.fxml", "Single game");
    }

    @FXML
    void back(ActionEvent event) throws IOException {
        showWindow(event, "Home.fxml", "Home");
    }

    void showWindow(ActionEvent event, String resource, String title) throws IOException {
        Node source = (Node)  event.getSource();
        Stage primaryStage  = (Stage) source.getScene().getWindow();
        primaryStage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(MemoryGame.class.getResource(resource));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }

}
