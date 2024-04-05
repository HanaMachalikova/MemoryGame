package com.memorygame;

import javafx.application.Platform;
import javafx.event.ActionEvent;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Thread class in charge of the server - client connection
 * Only at multiplayer mode
 * Sends and accepts messages about the game
 * Checks who won/lost last level and the whole game
 */
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

    OpenWindow op = new OpenWindow();


    public ServerThread(String role, String cport, int sport, ActionEvent event) {
        this.role = role;
        this.cport = cport;
        this.sport = sport;
        this.event = event;
    }

    private MsgMultiton mm;
    private ResultMultiton rm;
    private final EndSingleton es = EndSingleton.getInstance();

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

    /**
     * Method in charge of server part of connection
     * Accepts messages from client and send others there
     * @param portNumber for the server creation, needed for making connection with client
     */
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
                        op.showWindow(event, "Multi.fxml", "Multiplayer");
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
                    msgClient = bufferedReader.readLine();
                    rm = ResultMultiton.getInstance(String.valueOf(level));
                    String[] client = msgClient.split(";");
                    es.getNext_level().setVisible(true);
                    if (rm.isFailed() && client[0].equals("f")) {
                        gameResult("You both failed!", true, true, String.valueOf(rm.getTime()), client[1]);
                    } else if (rm.isFailed() || (rm.getTime() < Integer.parseInt(client[1]))) {
                        gameResult("You lost!", true, false, String.valueOf(rm.getTime()), client[1]);
                    } else if (client[0].equals("f") || (rm.getTime() > Integer.parseInt(client[1]))) {
                        gameResult("You won!", false, true, String.valueOf(rm.getTime()), client[1]);
                    } else if (rm.getTime() == Integer.parseInt(client[1])) {
                        gameResult("It's a draw!", false, false, String.valueOf(rm.getTime()), client[1]);

                    }
                    level++;
                    if (level == 3) {
                        finalResult(y, o);
                        y = 0;
                        o = 0;
                        es.getNew_game().setVisible(true);
                    }

                }
            } catch (IOException e) {
                try {
                    socket.close();
                    inputStreamReader.close();
                    outputStreamWriter.close();
                    bufferedReader.close();
                    bufferedWriter.close();
                    running = false;
                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    /**
     * Method in charge of client part of connection
     * Accepts messages from server and send others there
     * @param portNumber needed for making connection with server
     */
    public void client(String portNumber) {
        Socket socket = null;
        InputStreamReader inputStreamReader = null;
        OutputStreamWriter outputStreamWriter = null;
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;


        try {
            socket = new Socket("localhost", Integer.parseInt(portNumber));
            inputStreamReader = new InputStreamReader(socket.getInputStream());
            outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());

            bufferedReader = new BufferedReader(inputStreamReader);
            bufferedWriter = new BufferedWriter(outputStreamWriter);

            String r = "ready";
            bufferedWriter.write(r);
            bufferedWriter.newLine();
            bufferedWriter.flush();
            op.showWindow(event, "Multi.fxml", "Multiplayer");
            String msgServer;

            while (level <= 3) {
                while (true) {
                    mm = MsgMultiton.getInstance(String.valueOf(level));
                    msgFromClient = mm.getMessage();
                    if (!msgFromClient.isEmpty()) {
                        bufferedWriter.write(msgFromClient);
                        bufferedWriter.newLine();
                        bufferedWriter.flush();
                        msgFromClient = "";
                        break;
                    }
                }
                msgServer = bufferedReader.readLine();
                rm = ResultMultiton.getInstance(String.valueOf(level));
                String[] server = msgServer.split(";");
                es.getNext_level().setVisible(true);
                if (rm.isFailed() && server[0].equals("f")) {
                    gameResult("You both failed!", true, true, String.valueOf(rm.getTime()), server[1]);
                } else if (rm.isFailed() || (rm.getTime() < Integer.parseInt(server[1]))) {
                    gameResult("You lost!", true, false, String.valueOf(rm.getTime()), server[1]);
                } else if (server[0].equals("f") || (rm.getTime() > Integer.parseInt(server[1]))) {
                    gameResult("You won!", false, true, String.valueOf(rm.getTime()), server[1]);
                } else if (rm.getTime() == Integer.parseInt(server[1])) {
                    gameResult("It's a draw!", false, false, String.valueOf(rm.getTime()), server[1]);
                }
                level++;
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

    /**
     * Setting results from both players to scene of the game
     * @param result text of sign where is result of the last level
     * @param you if player lost
     * @param opponent if opponent lost
     * @param yTime time in which player finished last level
     * @param oTime time in which opponent finished last level
     */
    public void gameResult(String result, boolean you, boolean opponent, String yTime, String oTime) {
        Platform.runLater(() -> {
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
        });
    }

    /**
     * Setting final results of the game on the game scene
     * @param you how many times player won
     * @param opponent how many times opponent won
     */
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
