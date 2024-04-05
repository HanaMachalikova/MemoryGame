package com.memorygame;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

import java.io.IOException;

public class TutController {
    OpenWindow op = new OpenWindow();



    @FXML
    void go_back(ActionEvent event) throws IOException {
        op.showWindow(event, "Home.fxml", "Typing Game");
    }
}
