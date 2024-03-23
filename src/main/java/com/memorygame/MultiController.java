package com.memorygame;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class MultiController {

    @FXML
    private Button back;

    @FXML
    private Button home;

    @FXML
    private Label lastLevel;

    @FXML
    private AnchorPane no_previous;

    @FXML
    private Label o_lose;

    @FXML
    private Label o_win;

    @FXML
    private Label opponent;

    @FXML
    private Label score;

    @FXML
    private Button start;

    @FXML
    private Label time;

    @FXML
    private Label y_lose;

    @FXML
    private Label y_win;

    @FXML
    private Label you;

    @FXML
    private Label key;

    @FXML
    private TextField textField;

    @FXML
    private Pane pane;

    @FXML
    private Label score_number;

    @FXML
    private SplitPane splitPane;

    Start s;

    @FXML
    void start(ActionEvent event) throws InterruptedException {
        s = new Start(start, pane, score_number, key);
        s.start(event);

    }

    @FXML
    public void typed_key(KeyEvent event) throws InterruptedException {
        s.typed_key(event);
    }


    @FXML
    void go_home(ActionEvent event) throws IOException {
        showWindow(event, "Home.fxml", "Home");
    }

    @FXML
    void go_back(ActionEvent event) throws IOException {
        showWindow(event, "NewGame.fxml", "New Game");
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
