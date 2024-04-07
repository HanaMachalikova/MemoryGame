package com.memorygame;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for Multi.fxml – scene with multiplayer game
 */
public class MultiController implements Initializable {
    @FXML
    private Label finished;

    @FXML
    private Label game_over;

    @FXML
    private Label key;
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
    private Label score_number;

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
    private final char[] alphabet = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    private int sc_number = 0;
    private String letters = "";
    private String answer = "";
    private int lvl = 1;
    private int answerOrder = 0;
    private int letterOrder = 0;
    private long startTime;
    private long measuredTime;
    OpenWindow op = new OpenWindow();
    private final EndSingleton es = EndSingleton.getInstance();


    @FXML
    void start(ActionEvent event){
        lvl = 1;
        level_number.setText("1");
        sc_number = 0;
        measuredTime = 0;
        start.setVisible(false);
        startGame();

    }

    /**
     * Setting properties for the letter which is player supposed to press on keyboard
     */
    void startGame(){
        int coordinateX = (int) (Math.random() * (pane.getWidth() - pane.getLayoutY() - key.getWidth())+ 1 + pane.getLayoutY());
        int coordinateY = (int) (Math.random() * (pane.getHeight() - pane.getLayoutX() - key.getHeight())+ 1 + pane.getLayoutX());
        letters = "";
        answer = "";
        startTime = System.currentTimeMillis();
        score_number.setText(Integer.toString(sc_number));
        key.setVisible(true);
        key.setLayoutX(coordinateX);
        key.setLayoutY(coordinateY);
        for (int i = 0; i < lvl; i++) {
            letters += String.valueOf(alphabet[(int) (Math.random() * (alphabet.length))]);
        }
        key.setText(letters);
    }

    /**
     * A method controlling if any key on keyboard has been pressed
     * Checking if pressed key is same as displayed letter
     * Setting messages for server connection through MsgMultiton
     * @param event KeyEvent checking keyboard for any activity
     */
    @FXML
    public void typed_key(KeyEvent event) {
        String msg;
        letterOrder++;
        answer += event.getText();
        MsgMultiton mm;
        ResultMultiton rm;
        if ((letterOrder == lvl) && answer.equals(letters)) {
            key.setVisible(false);
            letterOrder = 0;
            sc_number++;
            answerOrder++;
            if (answerOrder == 15) {
                measuredTime = System.currentTimeMillis() - startTime;
                measuredTime += 10;
                y_lose.setVisible(false);
                y_win.setVisible(false);
                o_lose.setVisible(false);
                o_win.setVisible(false);
                time_number.setVisible(true);
                time_number.setText((measuredTime / 100) + "," + (measuredTime % 100) + " s");
                time.setVisible(true);
                rm = ResultMultiton.getInstance(String.valueOf(lvl));
                msg = "p;" + (measuredTime);
                mm = MsgMultiton.getInstance(String.valueOf(lvl));
                mm.setMessage(msg);
                rm.setFailed(false);
                rm.setTime(measuredTime);
                answerOrder = 0;
                score_number.setText(String.valueOf(sc_number));
                if (lvl <= 63) {
                    finished.setVisible(true);
                    finished.setText("You finished!");
                    result.setVisible(true);
                    result.setText("Waiting for your opponent");
                }else if (lvl == 6) {
                    game_over.setVisible(true);
                    game_over.setText("Game finished");
                    lvl = 1;
                    new_game.setVisible(true);
                }
            } else {
                startGame();
            }
        } else if (!answer.equals(letters.substring(0, letterOrder))) {
            key.setVisible(false);
            time.setVisible(true);
            time_number.setVisible(true);
            time_number.setText("failed");
            rm = ResultMultiton.getInstance(String.valueOf(lvl));
            rm.setTime(0);
            System.out.println("game over");
            msg = "f;" + (0);
            mm = MsgMultiton.getInstance(String.valueOf(lvl));
            mm.setMessage(msg);
            rm = ResultMultiton.getInstance(String.valueOf(lvl));
            rm.setFailed(true);
            letterOrder = 0;

            level_number.setText(String.valueOf(lvl));
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
        level_number.setText("");
    }

    @FXML
    void nextLevel(ActionEvent event) {
        textField.requestFocus();
        lvl++;
        finished.setVisible(false);
        result.setVisible(false);
        game_over.setVisible(false);
        next_level.setVisible(false);
        time.setVisible(false);
        time_number.setVisible(false);
        level_number.setText(String.valueOf(lvl));
        startGame();

    }


    @FXML
    void go_home(ActionEvent event) throws IOException {
        op.showWindow(event, "Home.fxml", "Home");
    }

    @FXML
    void go_back(ActionEvent event) throws IOException {
        op.showWindow(event, "NewGame.fxml", "New Game");
    }

    /**
     * Overriding method initialize which is launched when MultiController if firstly loaded
     * Initialising  variables for EndSingleton
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        es.setNext_level(next_level);
        es.setO_lose(o_lose);
        es.setO_win(o_win);
        es.setOpponent(opponent);
        es.setY_lose(y_lose);
        es.setY_win(y_win);
        es.setYou(you);
        es.setResult(result);
        es.setNo_previous(no_previous);
        es.setNew_game(new_game);
        es.setGame_over(game_over);

    }
}
