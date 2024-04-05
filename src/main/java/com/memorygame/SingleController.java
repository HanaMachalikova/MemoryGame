package com.memorygame;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

import java.io.IOException;

/**
 * Controller for Single.fxml taking care of the whole single player game
 */
public class SingleController {
    private int index;
    private final char[] alphabet = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
    private int coordinateX;
    private int coordinateY;
    private int sc_number = 0;
    private long startTime;
    private long measuredTime;

    @FXML
    private Pane pane;

    @FXML
    private Label score_number;

    @FXML
    private Button start;

    @FXML
    private Label key;

    @FXML
    private Label time_number;

    @FXML
    private Label game_over;

    OpenWindow op = new OpenWindow();

    @FXML
    void new_game(ActionEvent event) {
        game_over.setVisible(false);
        start.setVisible(true);
    }

    @FXML
    void go_home(ActionEvent event) throws IOException {
        op.showWindow(event, "Home.fxml", "Home");
    }

    @FXML
    void go_back(ActionEvent event) throws IOException {
        op.showWindow(event, "NewGame.fxml", "New Game");
    }

    @FXML
    void start(ActionEvent event) {
        sc_number = 0;
        measuredTime = 0;
        start.setVisible(false);
        System.out.println(pane.getLayoutX());
        System.out.println(pane.getLayoutY());
        startTime = System.currentTimeMillis();
        System.out.println(startTime + " ms");
        startGame();

    }

    void startGame() {
        score_number.setText(Integer.toString(sc_number));
        propertiesOfButton();
        key.setVisible(true);
        key.setLayoutX(coordinateX);
        key.setLayoutY(coordinateY);
        key.setText(String.valueOf(alphabet[index]));
        System.out.println("X: " + coordinateX);
        System.out.println("Y: " + coordinateY);
    }


    @FXML
    public void typed_key(KeyEvent event){
        System.out.println("Pressed");
            if (event.getText().equalsIgnoreCase(String.valueOf(alphabet[index]))) {
                key.setVisible(false);
                sc_number++;
                startGame();
            } else {
                measuredTime = System.currentTimeMillis() - startTime;
                System.out.println("game over");
                key.setVisible(false);
                game_over.setVisible(true);
                time_number.setText((measuredTime / 1000) + "," + (measuredTime % 1000) + " s");
            }

    }

    void propertiesOfButton(){
        index = (int) (Math.random() * (alphabet.length));
        coordinateX = (int) (Math.random() * (pane.getWidth() - pane.getLayoutY() - key.getWidth())+ 1 + pane.getLayoutY());
        coordinateY = (int) (Math.random() * (pane.getHeight() - pane.getLayoutX() - key.getHeight())+ 1 + pane.getLayoutX());

    }

}
