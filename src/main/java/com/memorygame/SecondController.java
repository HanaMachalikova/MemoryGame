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
import java.net.Socket;
import java.util.Scanner;

public class SecondController {

    @FXML
    private Button back;

    @FXML
    private TextField code_field;

    @FXML
    private Label invalid1;

    @FXML
    private Label invalid2;

    @FXML
    private Label second_text;

    @FXML
    private Button send;

    @FXML
    private Label text_enter;
    ServerThread st;
    EndSingleton es = EndSingleton.getInstance();

    @FXML
    void back(ActionEvent event) throws IOException {
        showWindow(event, "Connection.fxml", "Connection");
    }

    @FXML
    void send(ActionEvent event) throws IOException, InterruptedException {
        es.setText_enter(text_enter);
        es.setInvalid1(invalid1);
        es.setInvalid2(invalid2);
        es.setCode_field(code_field);
        String code = code_field.getText();
        System.out.println(code);
        System.out.println(Integer.valueOf(code));
        //try{
        //    serverSocket = new ServerSocket(Integer.valueOf(code));
        st = new ServerThread("client", code, 0, event);
        st.start();
        /*Thread.sleep(10000);
        System.out.println(s.ready);
        /*ReadyThread r = new ReadyThread(event, s);
        r.start();
        /*} catch (IOException e) {
            text_enter.setVisible(false);
            invalid1.setVisible(true);
            invalid2.setVisible(true);
            code_field.setText("");
        }*/

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

}
