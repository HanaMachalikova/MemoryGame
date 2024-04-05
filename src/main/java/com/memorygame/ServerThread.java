package com.memorygame;

import javafx.application.Platform;
import javafx.event.ActionEvent;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread extends Thread {

    String role;
    String cport;
    int sport;
    boolean ready;

    ActionEvent event;

    public static String msgFromClient = "";

    public static String msgFromServer = "";
    public int level = 1;

    public int y = 0;
    public int o = 0;

    public boolean running = true;

    public ServerThread(String role, String cport, int sport, ActionEvent event) {
        this.role = role;
        this.cport = cport;
        this.sport = sport;
        this.event = event;
    }

    private MsgMultiton mm;
    private ResultMultiton rm;
    private EndSingleton es = EndSingleton.getInstance();
    ;

    public void run() {
        while (running) {
            System.out.println(role);
            switch (role) {
                case "server":
                    try {
                        server(sport);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;

                case "client":
                    client(cport);

                    break;
            }
        }
    }

    public void server(int portNumber) throws IOException {
        Socket socket = null;
        InputStreamReader inputStreamReader = null;
        OutputStreamWriter outputStreamWriter = null;
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;

        ServerSocket serverSocket = new ServerSocket(portNumber);

        while (level <= 5) {
            try {
                socket = serverSocket.accept();

                inputStreamReader = new InputStreamReader(socket.getInputStream());
                outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());

                bufferedReader = new BufferedReader(inputStreamReader);
                bufferedWriter = new BufferedWriter(outputStreamWriter);
                String msgClient;
                while (true) {
                    msgClient = bufferedReader.readLine();
                    if (msgClient.equalsIgnoreCase("ready")) {
                        ready = true;
                        OpenWindow ow = new OpenWindow(event, "Multi.fxml", "Multiplayer");
                        ow.showWindow();
                    }
                    break;
                }

                while (true) {
                    while (true) {
                        mm = MsgMultiton.getInstance(String.valueOf(level));
                        msgFromServer = mm.getMessage();
                        if (!msgFromServer.isEmpty()) {
                            bufferedWriter.write(msgFromServer);
                            bufferedWriter.newLine();
                            bufferedWriter.flush();
                            System.out.println(msgFromServer);
                            msgFromServer = "";
                            break;

                        }
                    }
                    while (true) {
                        msgClient = bufferedReader.readLine();
                        if (!msgClient.isEmpty() && (msgClient != null)) {
                            break;
                        }
                    }
                    rm = ResultMultiton.getInstance(String.valueOf(level));
                    String[] client = msgClient.split(";");
                    es.getNext_level().setVisible(true);
                    System.out.println("me: " + rm.isFailed());
                    System.out.println("opponent: " + client[0]);
                    if (rm.isFailed() && client[0].equals("f")) {
                        gameResult("You both failed!", true, true, String.valueOf(rm.getTime()), client[1]);
                    } else if (rm.isFailed() || (rm.getTime() < Integer.valueOf(client[1]))) {
                        gameResult("You lost!", true, false, String.valueOf(rm.getTime()), client[1]);
                    } else if (client[0].equals("f") || (rm.getTime() > Integer.valueOf(client[1]))) {
                        gameResult("You won!", false, true, String.valueOf(rm.getTime()), client[1]);
                    } else if (rm.getTime() == Integer.valueOf(client[1])) {
                        gameResult("It's a draw!", false, false, String.valueOf(rm.getTime()), client[1]);
                    }
                    es.setReady(true);
                    if (msgClient.equalsIgnoreCase("BYE")) {
                        break;
                    }
                    level++;
                    if (level == 3) {
                        finalResult(y, o);
                        y = 0;
                        o = 0;
                        es.getNew_game().setVisible(true);
                    }

                }
                socket.close();
                inputStreamReader.close();
                outputStreamWriter.close();
                bufferedReader.close();
                bufferedWriter.close();
                running = false;
            } catch (IOException e) {
                socket.close();
                inputStreamReader.close();
                outputStreamWriter.close();
                bufferedReader.close();
                bufferedWriter.close();
                System.out.println("Chyba, vše ukončeno");
                running = false;
            }
        }
    }

    public void client(String portNumber) {
        Socket socket = null;
        InputStreamReader inputStreamReader = null;
        OutputStreamWriter outputStreamWriter = null;
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;


        try {
            socket = new Socket("localhost", Integer.valueOf(portNumber));
            inputStreamReader = new InputStreamReader(socket.getInputStream());
            outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());

            bufferedReader = new BufferedReader(inputStreamReader);
            bufferedWriter = new BufferedWriter(outputStreamWriter);

            String r = "ready";
            bufferedWriter.write(r);
            bufferedWriter.newLine();
            bufferedWriter.flush();
            OpenWindow ow = new OpenWindow(event, "Multi.fxml", "Multiplayer");
            ow.showWindow();
            String msgServer;

            while (level <= 3) {

                while (true) {
                    mm = MsgMultiton.getInstance(String.valueOf(level));
                    msgFromClient = mm.getMessage();
                    if (!msgFromClient.isEmpty()) {
                        bufferedWriter.write(msgFromClient);
                        bufferedWriter.newLine();
                        bufferedWriter.flush();
                        System.out.println(msgFromClient);
                        msgFromClient = "";
                        break;
                    }
                }
                while (true) {
                    msgServer = bufferedReader.readLine();
                    if (!msgServer.isEmpty() && (msgServer != null)) {
                        break;
                    }
                }
                rm = ResultMultiton.getInstance(String.valueOf(level));
                String[] server = msgServer.split(";");
                es.getNext_level().setVisible(true);
                System.out.println("me: " + rm.isFailed());
                System.out.println("opponent: " + server[0]);
                if (rm.isFailed() && server[0].equals("f")) {
                    gameResult("You both failed!", true, true, String.valueOf(rm.getTime()), server[1]);
                } else if (rm.isFailed() || (rm.getTime() < Integer.valueOf(server[1]))) {
                    gameResult("You lost!", true, false, String.valueOf(rm.getTime()), server[1]);
                } else if (server[0].equals("f") || (rm.getTime() > Integer.valueOf(server[1]))) {
                    gameResult("You won!", false, true, String.valueOf(rm.getTime()), server[1]);
                } else if (rm.getTime() == Integer.valueOf(server[1])) {
                    gameResult("It's a draw!", false, false, String.valueOf(rm.getTime()), server[1]);
                }
                level++;
                if (msgFromClient.equalsIgnoreCase("BYE"))
                    break;
                if (level == 3) {
                    finalResult(y, o);
                    y = 0;
                    o = 0;
                    es.getNew_game().setVisible(true);
                }
            }

        } catch (IOException e) {
            es.getText_enter().setVisible(false);
            es.getInvalid1().setVisible(true);
            es.getInvalid2().setVisible(true);
            es.getCode_field().setText("");
        } finally {
            running = false;
            try {
                if (socket != null)
                    socket.close();
                if (inputStreamReader != null)
                    inputStreamReader.close();
                if (outputStreamWriter != null)
                    outputStreamWriter.close();
                if (bufferedReader != null)
                    bufferedReader.close();
                if (bufferedWriter != null)
                    bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void gameResult(String result, boolean you, boolean opponent, String yTime, String oTime) {
        Platform.runLater(() -> {
            System.out.println(result);
            System.out.println("you: " + you);
            System.out.println("op: " + opponent);
            System.out.println("op Time: " + oTime);
            System.out.println("you time: " + yTime);
            es.getY_win().setVisible(false);
            es.getO_win().setVisible(false);
            es.getY_lose().setVisible(false);
            es.getO_lose().setVisible(false);
            es.getResult().setText(result);
            if (!you) {
                es.getY_win().setVisible(true);
            } else {
                es.getY_lose().setVisible(true);
            }
            if (!opponent) {
                es.getO_win().setVisible(true);
            } else {
                es.getO_lose().setVisible(true);
            }
            es.getYou().setText(yTime + " ms");
            es.getOpponent().setText(oTime + " ms");
            es.getNo_previous().setVisible(false);
            System.out.println("You: " + you);
            System.out.println("Opp: " + opponent);
        });
    }

    public void finalResult(int you, int opponent) {
        Platform.runLater(() -> {
            es.getGame_over().setText("Game finished");
            es.next_level.setVisible(false);
            if (you == opponent) {
                es.getResult().setText("It's a draw!");
            } else if (you > opponent) {
                es.getResult().setText("You won the whole game!");
            } else {
                es.getResult().setText("You lost the whole game!");
            }

        });
    }

}
