package com.memorygame;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class OpenWindow {

    ActionEvent event;
    String resource;
    String title;


    void showWindow(ActionEvent event, String resource, String title) throws IOException {
        Platform.runLater(() -> {
            Node source = (Node) event.getSource();
            Stage primarystage = (Stage) source.getScene().getWindow();
            primarystage.close();
            FXMLLoader fxmlLoader = new FXMLLoader(TypingGame.class.getResource(resource));
            Scene scene;
            try {
                scene = new Scene(fxmlLoader.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(scene);
            stage.show();
        });
    }
}
