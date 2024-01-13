package com.memorygame;

import com.memorygame.MemoryGame;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;

//import static com.memorygame.SingleController.typed_key;

public class HomeController {

    @FXML
    private Label welcome_label;

    @FXML
    private Button how_to_play;

    @FXML
    private Button new_game;


    @FXML
    private Button singleGame;

    @FXML
    void single_game(ActionEvent event) throws IOException {
        new_game(event);
    }


    @FXML
    void how_to_play(ActionEvent event) {

    }

    @FXML
    void new_game(ActionEvent event) throws IOException {
        Node source = (Node)  event.getSource();
        Stage primarystage  = (Stage) source.getScene().getWindow();
        primarystage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(MemoryGame.class.getResource("NewGame.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("New game");
        stage.setScene(scene);
        stage.show();
    }

}
