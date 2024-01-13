package com.memorygame;

import com.memorygame.MemoryGame;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class SingleController {

    private boolean game;
    private int index;
    private char[] alphabet = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
    private int coordinateX;
    private int coordinateY;
    private int sc_number = 0;
    private long startTime;
    private long measuredTime;

    @FXML
    private Pane pane;

    @FXML
    private Button back;

    @FXML
    private ButtonBar buttonBar;

    @FXML
    private Button home;


    @FXML
    private Label score;

    @FXML
    private Label score_number;

    @FXML
    private Button start;

    @FXML
    private Button new_game;

    @FXML
    private Label key;

    @FXML
    private TextField textField;


    @FXML
    private Label time;

    @FXML
    private Label time_number;

    @FXML
    void new_game(ActionEvent event) {
        start.setVisible(true);
    }

    @FXML
    void go_home(ActionEvent event) throws IOException {
        showWindow(event, "Home.fxml", "Home");
    }

    @FXML
    void go_back(ActionEvent event) throws IOException {
        showWindow(event, "NewGame.fxml", "New Game");
    }

    @FXML
    void start(ActionEvent event) throws InterruptedException {
        sc_number = 0;
        measuredTime = 0;
        game = true;
        start.setVisible(false);
        System.out.println(pane.getLayoutX());
        System.out.println(pane.getLayoutY());
        startTime = System.currentTimeMillis();
        System.out.println(startTime + " ms");
        startGame();

    }

    void startGame() throws InterruptedException {
        score_number.setText(Integer.toString(sc_number));
        propertiesOfButton();
        key.setVisible(true);
        key.setLayoutX(coordinateX);
        key.setLayoutY(coordinateY);
        //game = false;
        key.setText(String.valueOf(alphabet[index]));
        System.out.println("X: " + coordinateX);
        System.out.println("Y: " + coordinateY);
        //stopWatch();
    }


    @FXML
    public void typed_key(KeyEvent event) throws InterruptedException {
        System.out.println("Pressed");
        if (event.getText().equalsIgnoreCase(String.valueOf(alphabet[index]))) {
            key.setVisible(false);
            sc_number ++;
            startGame();
        }
        else {
            System.out.println("game over");
            game = false;
            key.setVisible(false);
            start.setVisible(true);
        }


    }

    /*public void stopWatch() throws InterruptedException {
        while (game) {
            System.out.println("running");
            time_number.setText((Long.toString(System.currentTimeMillis() - startTime)) + " ms");
            Thread.sleep(2000);

        }
    }*/

    void propertiesOfButton(){
        index = (int) (Math.random() * (alphabet.length));
        //coordinateX = (int) ((Math.random() * (pane.getWidth() + 1)) + pane.getLayoutX());
        //coordinateY = (int) ((Math.random() * (pane.getHeight() + 1)) + pane.getLayoutY());
        coordinateX = (int) ((Math.random() * pane.getWidth() + 1));
        coordinateY = (int) ((Math.random() * pane.getHeight() + 1));

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
