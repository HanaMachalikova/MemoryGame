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

    @FXML
    private Button back;

    @FXML
    private Button first;

    @FXML
    private Label heading;

    @FXML
    private Button second;

    @FXML
    void back(ActionEvent event) throws IOException {
        showWindow(event, "NewGame.fxml", "New game");

    }

    @FXML
    void first(ActionEvent event) throws IOException {
        showWindow(event, "First.fxml", "First player");

    }

    @FXML
    void second(ActionEvent event) throws IOException {
        showWindow(event, "Second.fxml", "Second player");

    }

    void showWindow(ActionEvent event, String resource, String title) throws IOException {
        Node source = (Node)  event.getSource();
        Stage primarystage  = (Stage) source.getScene().getWindow();
        primarystage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(MemoryGame.class.getResource(resource));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }

}

