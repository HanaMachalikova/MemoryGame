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

public class MultiStart {/*

    private boolean game;
    private char[] alphabet = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
    private int coordinateX;
    private int coordinateY;
    private int sc_number = 0;
    private String letters = "";
    private String answer = "";
    private int lvl = 1;
    private int answerOrder = 0;
    private int letterOrder = 0;
    private boolean failed;

    private long startTime;
    private long measuredTime;
    private Button start;
    private Pane pane;
    private Label score_number;
    private Label key;
    private Label game_over;
    private Label level;
    private ActionEvent event = null;

    ServerThread st = new ServerThread("", "", 0, event);

    public MultiStart(Button start, Pane pane, Label score_number, Label key, Label game_over, Label level) {
        this.start = start;
        this.pane = pane;
        this.score_number = score_number;
        this.key = key;
        this.game_over = game_over;
        this.level = level;
    }

    void start(ActionEvent event) throws InterruptedException {
        sc_number = 0;
        measuredTime = 0;
        game = true;
        start.setVisible(false);
        System.out.println(pane.getLayoutX());
        System.out.println(pane.getLayoutY());
        //startTime = System.currentTimeMillis();
        //System.out.println(startTime + " ms");
        startGame();

    }

    void startGame() throws InterruptedException {
        score_number.setText(Integer.toString(sc_number));
        propertiesOfButton();
        key.setVisible(true);
        key.setLayoutX(coordinateX);
        key.setLayoutY(coordinateY);
        //game = false;
        for (int i = 0; i < lvl; i++) {
            letters += String.valueOf(alphabet[(int) (Math.random() * (alphabet.length))]);
        }
        key.setText(letters);
        System.out.println("X: " + coordinateX);
        System.out.println("Y: " + coordinateY);
        //stopWatch();
    }


    @FXML
    public void typed_key(KeyEvent event) throws InterruptedException {
        System.out.println("Pressed");
        letterOrder++;
        answer += event.getText();
        if (letterOrder == lvl) {
            letterOrder = 0;
            if (answer.equals(letters)) {
                if (answerOrder == 15) {
                    MultiController mc = new MultiController();
                    mc.getY_lose().setVisible(false);
                    mc.getY_win().setVisible(false);
                    mc.getO_lose().setVisible(false);
                    mc.getO_win().setVisible(false);
                    if (st.first) {
                        st.msgFromServer = "p;1";
                    } else if (!(st.first)) {
                        st.msgFromClient = "p;1";
                    }
                    lvl++;
                    level.setText(Integer.toString(lvl));
                    answerOrder = 0;
                    failed = false;
                }
                key.setVisible(false);
                sc_number++;
                letters = "";
                answer = "";
                startGame();
                answerOrder++;

            } else {
                System.out.println("game over");
                if (st.first) {
                    st.msgFromServer = "f;0";
                } else if (!(st.first)) {
                    st.msgFromClient = "f;0";
                }
                game = false;
                key.setVisible(false);
                game_over.setVisible(true);
                lvl = 1;
                level.setText("1");
                answerOrder = 0;
                failed = true;
            }
        }


    }

    public boolean isFailed() {
        return failed;
    }

    /*public void stopWatch() throws InterruptedException {
        while (game) {
            System.out.println("running");
            time_number.setText((Long.toString(System.currentTimeMillis() - startTime)) + " ms");
            Thread.sleep(2000);

        }
    }*/

    /*void propertiesOfButton(){
        //coordinateX = (int) ((Math.random() * (pane.getWidth() + 1)) + pane.getLayoutX());
        //coordinateY = (int) ((Math.random() * (pane.getHeight() + 1)) + pane.getLayoutY());
        coordinateX = (int) ((Math.random() * pane.getWidth() + 1));
        coordinateY = (int) ((Math.random() * pane.getHeight() + 1));

    }*/

}
