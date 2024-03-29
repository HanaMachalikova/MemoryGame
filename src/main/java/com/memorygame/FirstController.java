package com.memorygame;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class FirstController {

    private int portNumber;

    @FXML
    private Button back;

    @FXML
    private Label code;

    @FXML
    private Label text_code;

    @FXML
    private Label text_player;

    @FXML
    private Button generator;

    boolean ready = false;

    @FXML
    void back(ActionEvent event) throws IOException {
        showWindow(event, "Connection.fxml", "Connection");

    }

    @FXML
    void generate(ActionEvent event) throws IOException, InterruptedException {
        generator.setVisible(false);
        portNumber = (int) ((Math.random() * 2000) + 1024);
        text_code.setVisible(true);
        code.setText(Integer.toString(portNumber));
        /*Thread h = new Thread(() -> {
            if (prepared() == true) {
                try {
                    showWindow(event, "Multi.fxml", "Multiplayer player one");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });*/
        System.out.println(portNumber);
        ServerThread s = new ServerThread("server", "0000", portNumber, ready, event);
        //s.start();
        System.out.println(portNumber);
        /*OpenWindow ow = new OpenWindow(event, "Multi.fxml", "Multiplayer");
        ow.showWindow();
        /*ReadyThread r = new ReadyThread(event, s);
        r.start();
        /*Thread h = new Thread(() -> {
            while (true) {
                if(v.ready == true) {
                    System.out.println("ready");;
                }
            }*/
            //if (prepared(v) == true) {
                //try {
                    //System.out.println("ready");
                    //showWindow(event, "Multi.fxml", "Multiplayer player one");
                /*} catch (IOException e) {
                    throw new RuntimeException(e);
                }*/
            //}
        //});
        //Thread.sleep(500);
        /*while (true){
            if (v.ready == true) {
                showWindow(event, "Multi.fxml", "Multiplayer");
                break;
            }
            Thread.sleep(1);
        }*/
    }

    /*boolean prepared (Thread t) {
        while (true) {
            if(t.ready == true) {
                return true;
            }
        }
    }*/

    public void server (int portNumber) throws IOException{
        Socket socket = null;
        InputStreamReader inputStreamReader = null;
        OutputStreamWriter outputStreamWriter = null;
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;

        ServerSocket serverSocket = new ServerSocket(portNumber);

        System.out.println("Server is ready");


        while (true) {
            try {
                socket = serverSocket.accept();

                inputStreamReader = new InputStreamReader(socket.getInputStream());
                outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());

                bufferedReader = new BufferedReader(inputStreamReader);
                bufferedWriter = new BufferedWriter(outputStreamWriter);

                while (true) {
                    String msgFromClient = bufferedReader.readLine();
                    if(msgFromClient.equalsIgnoreCase("ready")) {
                        ready = true;
                    }
                    System.out.println("Client: " + msgFromClient);

                    bufferedWriter.write("MSG recieved");
                    bufferedWriter.newLine();
                    bufferedWriter.flush();

                    if (msgFromClient.equalsIgnoreCase("BYE")) {
                        break;
                    }

                }

                socket.close();
                inputStreamReader.close();
                outputStreamWriter.close();
                bufferedReader.close();
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
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
