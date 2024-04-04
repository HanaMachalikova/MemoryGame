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

    private Multiton multiton;

    public boolean isReady() {
        return ready;
    }

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
                while(true) {
                    msgClient = bufferedReader.readLine();
                    if (msgClient.equalsIgnoreCase("ready")) {
                        ready = true;
                        OpenWindow ow = new OpenWindow(event, "MultiS.fxml", "Multiplayer");
                        ow.showWindow();
                    }
                    break;
                }

                while (true) {
                    multiton = Multiton.getInstance(String.valueOf(level), null);
                    msgFromServer = multiton.getMessage();
                    System.out.println("started while(true) 1");
                    System.out.println(msgFromServer.isEmpty());
                    while(true) {
                        if (!msgFromServer.isEmpty()) {
                            bufferedWriter.write(msgFromServer);
                            bufferedWriter.newLine();
                            bufferedWriter.flush();
                            System.out.println(msgFromServer);
                            System.out.println("printed empty");
                            msgFromServer = "";
                            break;
                        }
                    }
                    while (true) {
                        msgClient = bufferedReader.readLine();
                        if (!msgClient.isEmpty()) {
                            System.out.println("Client" + msgClient);
                            break;
                        }
                    }
                    MultiSController mc = new MultiSController();
                    if (mc.getLvl() > 1) {
                        String[] client = msgClient.split(";");
                        mc.getNext_level().setVisible(true);
                        if (mc.isFailed() && client[0].equals("f")) {
                            mc.getY_lose().setVisible(true);
                            mc.getYou().setText("failed");
                            mc.getO_lose().setVisible(true);
                            mc.getOpponent().setText("failed");
                            mc.getResult().setText("You both failed!");
                        } else if (mc.isFailed()) {
                            mc.getY_lose().setVisible(true);
                            mc.getYou().setText("failed");
                            mc.getResult().setText("You failed!");
                        } else if (client[0].equals("f")) {
                            mc.getOpponent().setText("failed");
                            mc.getO_lose().setVisible(true);
                            if (mc.isFailed()) {
                                mc.getResult().setText("You won!");
                            }
                        } else if (mc.getMeasuredTime() > Integer.getInteger(client[1])) {
                            mc.getO_lose().setVisible(true);
                            mc.getY_win().setVisible(true);
                            mc.getOpponent().setText(client[1]);
                            mc.getYou().setText(Long.toString(mc.getMeasuredTime()));
                            mc.getResult().setText("You won!");
                        } else if (mc.getMeasuredTime() < Integer.getInteger(client[1])) {
                            mc.getO_win().setVisible(true);
                            mc.getY_lose().setVisible(true);
                            mc.getOpponent().setText(client[1]);
                            mc.getYou().setText(Long.toString(mc.getMeasuredTime()));
                            mc.getResult().setText("You lost!");
                        } else if (mc.getMeasuredTime() == Integer.getInteger(client[1])) {
                            mc.getO_win().setVisible(true);
                            mc.getY_win().setVisible(true);
                            mc.getOpponent().setText(client[1]);
                            mc.getYou().setText(Long.toString(mc.getMeasuredTime()));
                            mc.getResult().setText("It's a draw!");
                        }
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
                multiton = Multiton.getInstance(String.valueOf(level), null);
                msgFromClient = multiton.getMessage();
                System.out.println("started while(true) 1");
                System.out.println(msgFromClient.isEmpty());

                while (true) {
                    if (!msgFromClient.isEmpty()) {
                        bufferedWriter.write(msgFromClient);
                        bufferedWriter.newLine();
                        bufferedWriter.flush();
                        System.out.println(msgFromClient);
                        System.out.println("printed empty");
                        msgFromClient = "";
                        break;
                    }
                }
                while (true) {
                    msgServer = bufferedReader.readLine();
                    if (!msgServer.isEmpty()) {
                        System.out.println("Server: " + msgServer);
                        break;
                    }
                }
                MultiSController mc = new MultiSController();
                if (mc.getLvl() > 1) {
                    String[] server = msgServer.split(";");
                    mc.getNext_level().setVisible(true);
                    if (mc.isFailed() && server[0].equals("f")) {
                        mc.getY_lose().setVisible(true);
                        mc.getYou().setText("failed");
                        mc.getO_lose().setVisible(true);
                        mc.getOpponent().setText("failed");
                        mc.getResult().setText("You both failed!");
                    } else if (mc.isFailed()) {
                        mc.getY_lose().setVisible(true);
                        mc.getYou().setText("failed");
                        mc.getResult().setText("You failed!");
                    } else if (server[0].equals("f")) {
                        mc.getOpponent().setText("failed");
                        mc.getO_lose().setVisible(true);
                        if (mc.isFailed()) {
                            mc.getResult().setText("You won!");
                        }
                    } else if (mc.getMeasuredTime() > Integer.getInteger(server[1])) {
                        mc.getO_lose().setVisible(true);
                        mc.getY_win().setVisible(true);
                        mc.getOpponent().setText(server[1]);
                        mc.getYou().setText(Long.toString(mc.getMeasuredTime()));
                        mc.getResult().setText("You won!");
                    } else if (mc.getMeasuredTime() < Integer.getInteger(server[1])) {
                        mc.getO_win().setVisible(true);
                        mc.getY_lose().setVisible(true);
                        mc.getOpponent().setText(server[1]);
                        mc.getYou().setText(Long.toString(mc.getMeasuredTime()));
                        mc.getResult().setText("You lost!");
                    } else if (mc.getMeasuredTime() == Integer.getInteger(server[1])) {
                        mc.getO_win().setVisible(true);
                        mc.getY_win().setVisible(true);
                        mc.getOpponent().setText(server[1]);
                        mc.getYou().setText(Long.toString(mc.getMeasuredTime()));
                        mc.getResult().setText("It's a draw!");
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
