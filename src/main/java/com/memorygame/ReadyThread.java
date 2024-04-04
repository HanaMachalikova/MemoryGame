package com.memorygame;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ReadyThread extends Thread{

    ActionEvent event;
    ServerThread s;

    public ReadyThread(ActionEvent event, ServerThread s) {
        this.event = event;
        this.s = s;
    }

    public void run() {
        System.out.println("running thread");
        while (true){
            if (s.ready == true) {
                System.out.println("got ready true");
                try {
                    showWindow(event, "MultiS.fxml", "Multiplayer");
                    System.out.println("shown window");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            }
        }
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
