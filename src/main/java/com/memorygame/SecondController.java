package com.memorygame;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.io.*;

/**
 * Controller for Second.fxml
 * Need to write down code creating connection between two players
 * After sending right code this player represents client
 */
public class SecondController {
    @FXML
    private TextField code_field;

    @FXML
    private Label invalid1;

    @FXML
    private Label invalid2;

    @FXML
    private Label text_enter;
    ServerThread st;
    EndSingleton es = EndSingleton.getInstance();
    OpenWindow op = new OpenWindow();


    @FXML
    void back(ActionEvent event) throws IOException {
        op.showWindow(event, "Connection.fxml", "Connection");
    }

    @FXML
    void send(ActionEvent event) {
        es.setText_enter(text_enter);
        es.setInvalid1(invalid1);
        es.setInvalid2(invalid2);
        es.setCode_field(code_field);
        String code = code_field.getText();
        st = new ServerThread("client", code, 0, event);
        st.start();
    }
}
