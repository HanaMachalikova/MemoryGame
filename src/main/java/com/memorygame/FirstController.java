package com.memorygame;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.io.*;

/**
 * Controller for First.fxml
 * Generating code for creating connection between two players
 * After generation this player represents server
 */
public class FirstController {
    @FXML
    private Label code;

    @FXML
    private Label text_code;

    @FXML
    private Button generator;

    ServerThread st;

    OpenWindow op = new OpenWindow();

    @FXML
    void back(ActionEvent event) throws IOException {
        op.showWindow(event, "Connection.fxml", "Connection");

    }

    @FXML
    void generate(ActionEvent event) {
        int portNumber;
        generator.setVisible(false);
        portNumber = (int) ((Math.random() * 2000) + 1024);
        text_code.setVisible(true);
        code.setText(Integer.toString(portNumber));
        System.out.println(portNumber);
        st = new ServerThread("server", "0000", portNumber, event);
        st.start();
        System.out.println(portNumber);

    }
}
