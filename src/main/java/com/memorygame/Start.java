package com.memorygame;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Start {

    private boolean game;
    private int index;
    private char[] alphabet = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
    private int coordinateX;
    private int coordinateY;
    private int sc_number = 0;
    private long startTime;
    private long measuredTime;
    private Button start;
    private Pane pane;
    private Label score_number;
    private Label key;

    public Start(Button start, Pane pane, Label score_number, Label key) {
        this.start = start;
        this.pane = pane;
        this.score_number = score_number;
        this.key = key;
    }

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

}
