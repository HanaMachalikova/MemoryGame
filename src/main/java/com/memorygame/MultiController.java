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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class MultiController {
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
    private Label no_previous;

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
    private char[] alphabet = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    private int coordinateX;
    private int coordinateY;
    private int sc_number = 0;
    private String letters = "";
    private String answer = "";
    private int lvl = 1;
    private int answerOrder = 0;
    private int letterOrder = 0;
    private long startTime;
    private long measuredTime;
    private String msg;
    private MsgMultiton mm;
    private ResultMultiton rm;
    private EndSingleton es = EndSingleton.getInstance();


    @FXML
    void start(ActionEvent event) throws InterruptedException {
        es.setNext_level(next_level);
        es.setO_lose(o_lose);
        es.setO_win(o_win);
        es.setOpponent(opponent);
        es.setY_lose(y_lose);
        es.setY_win(y_win);
        es.setYou(you);
        es.setResult(result);
        es.setNo_previous(no_previous);
        es.setTime(time_number);
        es.setNew_game(new_game);
        es.setGame_over(game_over);
        es.setFinished(finished);
        lvl = 1;
        sc_number = 0;
        measuredTime = 0;
        start.setVisible(false);
        //System.out.println(pane.getLayoutX());
        //System.out.println(pane.getLayoutY());
        //System.out.println(startTime + " ms");
        startGame();
        level_number.setText(String.valueOf(1));

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
        if ((letterOrder == lvl) && answer.equals(letters)) {
            key.setVisible(false);
            letterOrder = 0;
            sc_number++;
            answerOrder++;
            if (answerOrder == 5) {
                measuredTime = System.currentTimeMillis() - startTime;
                y_lose.setVisible(false);
                y_win.setVisible(false);
                o_lose.setVisible(false);
                o_win.setVisible(false);
                rm = ResultMultiton.getInstance(String.valueOf(lvl));
                msg = "p;" + (measuredTime);
                mm = MsgMultiton.getInstance(String.valueOf(lvl));
                mm.setMessage(msg);
                rm.setFailed(false);
                rm.setTime(measuredTime);
                lvl++;
                answerOrder = 0;
                time.setVisible(true);
                time_number.setText(measuredTime + " ms");
                if (lvl <= 3) {
                    finished.setVisible(true);
                    finished.setText("You finished!");
                    result.setVisible(true);
                    result.setText("Waiting for your opponent");
                }else if (lvl == 4) {
                    game_over.setVisible(true);
                    game_over.setText("Game finished");
                    lvl = 1;
                    new_game.setVisible(true);
                }
            } else {
                startGame();
            }
        } else if (!answer.equals(letters.substring(0, answer.length()))) {
            key.setVisible(false);
            time.setVisible(true);
            time_number.setText("failed");
            rm = ResultMultiton.getInstance(String.valueOf(lvl));
            rm.setTime(0);
            System.out.println("game over");
            msg = "f;" + (0);
            mm = MsgMultiton.getInstance(String.valueOf(lvl));
            mm.setMessage(msg);
            rm = ResultMultiton.getInstance(String.valueOf(lvl));
            rm.setFailed(true);
            lvl++;
            if (lvl == 4) {
                game_over.setVisible(true);
                game_over.setText("Game finished");
                lvl = 1;
                new_game.setVisible(true);
            } else {
                finished.setVisible(true);
                finished.setText("You failed this level");
                result.setVisible(true);
                result.setText("Waiting for your opponent");
            }
            answerOrder = 0;
        }
    }

    @FXML
    void newGame(ActionEvent event) {
        start.setVisible(true);
        game_over.setVisible(false);
        result.setVisible(false);
        new_game.setVisible(false);
        finished.setVisible(false);
        lvl = 1;
    }

    @FXML
    void nextLevel(ActionEvent event) throws InterruptedException {
        textField.requestFocus();
        startGame();
        level_number.setText(Integer.toString(lvl));
        finished.setVisible(false);
        result.setVisible(false);
        game_over.setVisible(false);
        next_level.setVisible(false);
        time.setVisible(false);
        time_number.setVisible(false);

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
        Node source = (Node) event.getSource();
        Stage primarystage = (Stage) source.getScene().getWindow();
        primarystage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(MemoryGame.class.getResource(resource));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }

    void propertiesOfButton() {
        //coordinateX = (int) ((Math.random() * (pane.getWidth() + 1)) + pane.getLayoutX());
        //coordinateY = (int) ((Math.random() * (pane.getHeight() + 1)) + pane.getLayoutY());
        coordinateX = (int) ((Math.random() * pane.getWidth() + 1));
        coordinateY = (int) ((Math.random() * pane.getHeight() + 1));

    }
}
