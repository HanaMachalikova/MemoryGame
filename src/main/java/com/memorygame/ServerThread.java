package com.memorygame;

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

    public ServerThread(String role, String cport, int sport, ActionEvent event) {
        this.role = role;
        this.cport = cport;
        this.sport = sport;
        this.event = event;
    }

    private MsgMultiton mm;
    private ResultMultiton rm;
    private ConSingleton cs;


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
                    cs.getNext_level().setVisible(true);
                    if (rm.isFailed() && client[0].equals("f")) {
                        cs.getY_lose().setVisible(true);
                        cs.getYou().setText("failed");
                        cs.getO_lose().setVisible(true);
                        cs.getOpponent().setText("failed");
                        cs.getResult().setText("You both failed!");
                    } else if (rm.isFailed()) {
                        cs.getY_lose().setVisible(true);
                        cs.getYou().setText("failed");
                        cs.getResult().setText("You failed!");
                    } else if (client[0].equals("f")) {
                        cs.getOpponent().setText("failed");
                        cs.getO_lose().setVisible(true);
                        if (rm.isFailed()) {
                            cs.getResult().setText("You won!");
                        }
                    } else if (rm.getTime() > Integer.getInteger(client[1])) {
                        cs.getO_lose().setVisible(true);
                        cs.getY_win().setVisible(true);
                        cs.getOpponent().setText(client[1]);
                        cs.getYou().setText(Long.toString(rm.getTime()));
                        cs.getResult().setText("You won!");
                    } else if (rm.getTime() < Integer.getInteger(client[1])) {
                        cs.getO_win().setVisible(true);
                        cs.getY_lose().setVisible(true);
                        cs.getOpponent().setText(client[1]);
                        cs.getYou().setText(Long.toString(rm.getTime()));
                        cs.getResult().setText("You lost!");
                    } else if (rm.getTime() == Integer.getInteger(client[1])) {
                        cs.getO_win().setVisible(true);
                        cs.getY_win().setVisible(true);
                        cs.getOpponent().setText(client[1]);
                        cs.getYou().setText(Long.toString(rm.getTime()));
                        cs.getResult().setText("It's a draw!");
                    }


                    if (msgClient.equalsIgnoreCase("BYE")) {
                        break;
                    }
                    level++;

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

            while (level <= 5) {

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
                MultiSController rm = new MultiSController();
                if (rm.getLvl() > 1) {
                    String[] server = msgServer.split(";");
                    rm.getNext_level().setVisible(true);
                    if (rm.isFailed() && server[0].equals("f")) {
                        rm.getY_lose().setVisible(true);
                        rm.getYou().setText("failed");
                        rm.getO_lose().setVisible(true);
                        rm.getOpponent().setText("failed");
                        rm.getResult().setText("You both failed!");
                    } else if (rm.isFailed()) {
                        rm.getY_lose().setVisible(true);
                        rm.getYou().setText("failed");
                        rm.getResult().setText("You failed!");
                    } else if (server[0].equals("f")) {
                        rm.getOpponent().setText("failed");
                        rm.getO_lose().setVisible(true);
                        if (rm.isFailed()) {
                            rm.getResult().setText("You won!");
                        }
                    } else if (rm.getMeasuredTime() > Integer.getInteger(server[1])) {
                        rm.getO_lose().setVisible(true);
                        rm.getY_win().setVisible(true);
                        rm.getOpponent().setText(server[1]);
                        rm.getYou().setText(Long.toString(rm.getMeasuredTime()));
                        rm.getResult().setText("You won!");
                    } else if (rm.getMeasuredTime() < Integer.getInteger(server[1])) {
                        rm.getO_win().setVisible(true);
                        rm.getY_lose().setVisible(true);
                        rm.getOpponent().setText(server[1]);
                        rm.getYou().setText(Long.toString(rm.getMeasuredTime()));
                        rm.getResult().setText("You lost!");
                    } else if (rm.getMeasuredTime() == Integer.getInteger(server[1])) {
                        rm.getO_win().setVisible(true);
                        rm.getY_win().setVisible(true);
                        rm.getOpponent().setText(server[1]);
                        rm.getYou().setText(Long.toString(rm.getMeasuredTime()));
                        rm.getResult().setText("It's a draw!");
                    }
                }
                level++;
                if (msgFromClient.equalsIgnoreCase("BYE"))
                    break;
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
}
