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
    private Label time_number;

    @FXML
    private SplitPane splitPane;

    @FXML
    private Label game_over;

    @FXML
    private Label level;

    @FXML
    private Label level_number;

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
    private ActionEvent event = null;

    ServerThread st = new ServerThread("", "", 0, event);


    @FXML
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
