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

public class MultiSController {
    @FXML
    private Button back;

    @FXML
    private Label finished;

    @FXML
    private Label game_over;

    @FXML
    private Button home;

    @FXML
    private Label key;

    @FXML
    private Label lastLevel;

    @FXML
    private Label level;

    @FXML
    private Label level_number;

    @FXML
    private Button new_game;

    @FXML
    private Button next_level;

    @FXML
    private AnchorPane no_previous;

    @FXML
    private Label o_lose;

    @FXML
    private Label o_win;

    @FXML
    private Label opponent;

    @FXML
    private Pane pane;

    @FXML
    private Label result;

    @FXML
    private Label score;

    @FXML
    private Label score_number;

    @FXML
    private SplitPane splitPane;

    @FXML
    private Button start;

    @FXML
    private TextField textField;

    @FXML
    private Label time;

    @FXML
    private Label time_number;

    @FXML
    private Label y_lose;

    @FXML
    private Label y_win;

    @FXML
    private Label you;
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

    private Multiton multiton;


    @FXML
    void start(ActionEvent event) throws InterruptedException {
        sc_number = 0;
        measuredTime = 0;
        start.setVisible(false);
        //System.out.println(pane.getLayoutX());
        //System.out.println(pane.getLayoutY());
        //System.out.println(startTime + " ms");
        startGame();

    }

    void startGame() throws InterruptedException {
        letters = "";
        answer = "";
        startTime = System.currentTimeMillis();
        score_number.setText(Integer.toString(sc_number));
        propertiesOfButton();
        key.setVisible(true);
        key.setLayoutX(coordinateX);
        key.setLayoutY(coordinateY);
        for (int i = 0; i < lvl; i++) {
            letters += String.valueOf(alphabet[(int) (Math.random() * (alphabet.length))]);
        }
        key.setText(letters);
        //System.out.println("X: " + coordinateX);
        //System.out.println("Y: " + coordinateY);
        //stopWatch();
    }

    @FXML
    public void typed_key(KeyEvent event) throws InterruptedException {
        letterOrder++;
        answer += event.getText();
        if (letterOrder == lvl) {
            letterOrder = 0;
            if (answer.equals(letters)) {
                sc_number++;
                answerOrder++;
                if (answerOrder == 5) {
                    y_lose.setVisible(false);
                    y_win.setVisible(false);
                    o_lose.setVisible(false);
                    o_win.setVisible(false);
                    multiton = Multiton.getInstance(Integer.toString(lvl), "p;" + (System.currentTimeMillis() - startTime));
                    lvl++;
                    answerOrder = 0;
                    failed = false;
                    key.setVisible(false);
                    if (lvl <= 5) {
                        key.setVisible(false);
                        finished.setVisible(true);
                        finished.setText("You finished!");
                        result.setVisible(true);
                        result.setText("Waiting for your opponent");
                    }
                    if (lvl == 6) {
                        key.setVisible(false);
                        game_over.setVisible(true);
                        game_over.setText("Game finished");
                        lvl = 0;
                        new_game.setVisible(true);
                    }
                } else {
                    startGame();
                }

            } else {
                System.out.println("game over");
                multiton = Multiton.getInstance(Integer.toString(lvl), "f;" + (System.currentTimeMillis() - startTime));
                key.setVisible(false);
                finished.setVisible(true);
                finished.setText("You failed!");
                result.setVisible(true);
                result.setText("Waiting for your opponent");
                lvl = 1;
                level_number.setText("1");
                answerOrder = 0;
                failed = true;
            }
        }
    }

    @FXML
    void newGame(ActionEvent event) {
        game_over.setText("Game over");
        game_over.setVisible(false);
        start.setVisible(true);
    }

    @FXML
    void nextLevel(ActionEvent event) {
        level_number.setText(Integer.toString(lvl));
        finished.setVisible(false);
        result.setVisible(false);
        next_level.setVisible(false);

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

    void propertiesOfButton(){
        //coordinateX = (int) ((Math.random() * (pane.getWidth() + 1)) + pane.getLayoutX());
        //coordinateY = (int) ((Math.random() * (pane.getHeight() + 1)) + pane.getLayoutY());
        coordinateX = (int) ((Math.random() * pane.getWidth() + 1));
        coordinateY = (int) ((Math.random() * pane.getHeight() + 1));

    }
    public Button getNext_level() {
        return next_level;
    }

    public Label getResult() {
        return result;
    }

    public int getLvl() {
        return lvl;
    }

    public boolean isFailed() {
        return failed;
    }

    public long getMeasuredTime() {
        return measuredTime;
    }

    public Label getO_lose() {
        return o_lose;
    }

    public Label getO_win() {
        return o_win;
    }

    public Label getOpponent() {
        return opponent;
    }

    public Label getY_lose() {
        return y_lose;
    }

    public Label getY_win() {
        return y_win;
    }

    public Label getYou() {
        return you;
    }

}
