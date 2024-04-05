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

    boolean first;

    public static String msgFromClient = "";

    public static String msgFromServer = "";
    public int level = 1;

    public int y = 0;
    public int o = 0;

    public ServerThread(String role, String cport, int sport, ActionEvent event) {
        this.role = role;
        this.cport = cport;
        this.sport = sport;
        this.event = event;
    }

    private MsgMultiton mm;
    private ResultMultiton rm;
    private EndSingleton es = EndSingleton.getInstance();;


    public void setFirst(boolean first) {
        this.first = first;
    }

    /*public void setMsgFromClient(String msgFromClient) {
        this.msgFromClient = msgFromClient;
    }

    public void setMsgFromServer(String msgFromServer) {
        this.msgFromServer = msgFromServer;
    }*/

    public void run() {
        switch (role) {
            case "server":
                try {
                    server(sport);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;

            case "client":
                System.out.println("Client");
                client(cport);

                break;
        }
    }

    public void server(int portNumber) throws IOException {
        Socket socket = null;
        InputStreamReader inputStreamReader = null;
        OutputStreamWriter outputStreamWriter = null;
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;

        ServerSocket serverSocket = new ServerSocket(portNumber);

        System.out.println("Server is ready");


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
                        OpenWindow ow = new OpenWindow(event, "MultiS.fxml", "Multiplayer");
                        ow.showWindow();
                    }
                    break;
                }

                while (true) {
                    System.out.println("started while(true) 1");
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
                    System.out.println("prepared to read");
                    while (true) {
                        msgClient = bufferedReader.readLine();
                        if (!msgClient.isEmpty() && (msgClient != null)) {
                            System.out.println("Client" + msgClient);
                            break;
                        }
                    }
                    rm = ResultMultiton.getInstance(String.valueOf(level));
                    String[] client = msgClient.split(";");
                    System.out.println("0: " + client[0]);
                    System.out.println("1: " + client[1]);
                    es.getNext_level().setVisible(true);
                    if (rm.isFailed() && client[0].equals("f")) {
                        gameResult("You both failed!", false, false, String.valueOf(rm.getTime()), client[1]);
                    } else if (rm.isFailed() || (rm.getTime() > Integer.valueOf(client[1]))) {
                        o++;
                        gameResult("You lost!", false, true, String.valueOf(rm.getTime()), client[1]);
                    } else if (client[0].equals("f") || (rm.getTime() < Integer.valueOf(client[1]))) {
                        y++;
                        gameResult("You won!", true, false, String.valueOf(rm.getTime()), client[1]);
                    } else if (rm.getTime() == Integer.valueOf(client[1])) {
                        o++;
                        y++;
                        gameResult("It's a draw!", true, true, String.valueOf(rm.getTime()), client[1]);
                    }
                    es.setReady(true);
                    if (msgClient.equalsIgnoreCase("BYE")) {
                        break;
                    }
                    level++;
                    if(level == 3) {
                        finalResult(y, o);
                        y = 0;
                        o = 0;
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

            //Scanner sc = new Scanner(System.in);
            System.out.println("Client is ready");
            String r = "ready";
            bufferedWriter.write(r);
            bufferedWriter.newLine();
            bufferedWriter.flush();
            OpenWindow ow = new OpenWindow(event, "MultiC.fxml", "Multiplayer");
            ow.showWindow();
            String msgServer;

            while (level <= 3) {

                System.out.println("started while(true) 1");

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
                System.out.println("prepared to read");
                while (true) {
                    msgServer = bufferedReader.readLine();
                    if (!msgServer.isEmpty() && (msgServer != null)) {
                        System.out.println("Server" + msgServer);
                        break;
                    }
                }
                rm = ResultMultiton.getInstance(String.valueOf(level));
                String[] server = msgServer.split(";");
                System.out.println("0: " + server[0]);
                System.out.println("1: " + server[1]);
                es.getNext_level().setVisible(true);
                if (rm.isFailed() && server[0].equals("f")) {
                    gameResult("You both failed!", false, false, String.valueOf(rm.getTime()), server[1]);
                } else if (rm.isFailed() || (rm.getTime() > Integer.valueOf(server[1]))) {
                    gameResult("You lost!", false, true, String.valueOf(rm.getTime()), server[1]);
                } else if (server[0].equals("f") || (rm.getTime() < Integer.valueOf(server[1]))) {
                    gameResult("You won!", true, false, String.valueOf(rm.getTime()), server[1]);
                } else if (rm.getTime() == Integer.valueOf(server[1])) {
                    gameResult("It's a draw!", true, true, String.valueOf(rm.getTime()), server[1]);
                }
                level++;
                if (msgFromClient.equalsIgnoreCase("BYE"))
                    break;
                if(level == 3) {
                    finalResult(y, o);
                    y = 0;
                    o = 0;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
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

    public void gameResult (String result, boolean you, boolean opponent, String yTime, String oTime) {
        Platform.runLater(() -> {
            es.getY_win().setVisible(false);
            es.getO_win().setVisible(false);
            es.getY_lose().setVisible(false);
            es.getO_lose().setVisible(false);
            es.getResult().setText(result);
            if (you) {
                es.getY_win().setVisible(true);
            } else {
                es.getY_lose().setVisible(true);
            }
            if (opponent) {
                es.getO_win().setVisible(true);
            } else {
                es.getO_lose().setVisible(true);
            };
            es.getYou().setText(yTime + " ms");
            es.getOpponent().setText(oTime + " ms");
            es.getNo_previous().setVisible(false);
            es.getTime().setText(yTime + " ms");
            System.out.println("You: " + you);
            System.out.println("Opp: " + opponent);
        });
    }

    public void finalResult (int you, int opponent) {
        System.out.println("final results");
        Platform.runLater(() -> {
            es.next_level.setVisible(false);
            if (you == opponent) {
                es.getResult().setText("Whole game it's a draw!");
            } else if (you > opponent) {
                es.getResult().setText("You won the whole game!");
            } else {
                es.getResult().setText("You lost the whole game!");
            }

        });
    }

}
