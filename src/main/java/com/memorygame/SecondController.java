package com.memorygame;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;

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
