package com.memorygame;

import javafx.event.ActionEvent;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServerThread extends  Thread{

    String role;
    String cport;
    int sport;
    boolean ready;

    ActionEvent event;

    boolean first;

    public String msgFromClient = "";

    public String msgFromServer = "";

    public ServerThread(String role, String cport, int sport, ActionEvent event) {
        this.role = role;
        this.cport = cport;
        this.sport = sport;
        this.event = event;
    }

    public boolean isReady() {
        return ready;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public void setmsgFromClient(String msgFromClient) {
        this.msgFromClient = msgFromClient;
    }

    public void setMsgFromServer(String msgFromServer) {
        this.msgFromServer = msgFromServer;
    }

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
                    String msgClient = bufferedReader.readLine();
                    if(msgClient.equalsIgnoreCase("ready")) {
                        ready = true;
                        OpenWindow ow = new OpenWindow(event, "Multi.fxml", "Multiplayer");
                        ow.showWindow();
                    }
                    System.out.println("ready: " + ready);
                    System.out.println("Client: " + msgClient);
                    String[] client = msgClient.split(";");
                    MultiController mc = new MultiController();
                    if(client[0].equals("f")) {
                        mc.getOpponent().setText("failed");
                        mc.getO_lose().setVisible(true);
                    } else if (mc.isFailed()) {
                        mc.getY_lose().setVisible(true);
                        mc.getYou().setText("failed");
                    } else if (mc.getMeasuredTime() > Integer.getInteger(client[1])) {
                        mc.getO_lose().setVisible(true);
                        mc.getY_win().setVisible(true);
                        mc.getOpponent().setText(client[1]);
                        mc.getYou().setText(Long.toString(mc.getMeasuredTime()));
                    } else if (mc.getMeasuredTime() < Integer.getInteger(client[1])) {
                        mc.getO_win().setVisible(true);
                        mc.getY_lose().setVisible(true);
                        mc.getOpponent().setText(client[1]);
                        mc.getYou().setText(Long.toString(mc.getMeasuredTime()));
                    } else if (mc.getMeasuredTime() == Integer.getInteger(client[1])) {
                        mc.getO_win().setVisible(true);
                        mc.getY_win().setVisible(true);
                        mc.getOpponent().setText(client[1]);
                        mc.getYou().setText(Long.toString(mc.getMeasuredTime()));
                    }
                    bufferedWriter.write(msgFromServer);
                    bufferedWriter.newLine();
                    bufferedWriter.flush();

                    msgFromServer = "";

                    if (msgClient.equalsIgnoreCase("BYE")) {
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

    public void client (String portNumber)  {
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

            Scanner sc = new Scanner(System.in);
            System.out.println("Client is ready");
            String r = "ready";
            bufferedWriter.write(r);
            bufferedWriter.newLine();
            bufferedWriter.flush();
            OpenWindow ow = new OpenWindow(event, "Multi.fxml", "Multiplayer");
            ow.showWindow();

            while (true) {
                if (!msgFromClient.isEmpty()) {
                    String msgFromClient = sc.nextLine();
                    bufferedWriter.write(msgFromClient);
                    bufferedWriter.newLine();
                    bufferedWriter.flush();

                    String msgServer = bufferedReader.readLine();
                    System.out.println("Server: " + msgServer);
                    String[] client = msgServer.split(";");
                    MultiController mc = new MultiController();
                    if(client[0].equals("f")) {
                        mc.getOpponent().setText("failed");
                        mc.getO_lose().setVisible(true);
                    } else if (mc.isFailed()) {
                        mc.getY_lose().setVisible(true);
                        mc.getYou().setText("failed");
                    } else if (mc.getMeasuredTime() > Integer.getInteger(client[1])) {
                        mc.getO_lose().setVisible(true);
                        mc.getY_win().setVisible(true);
                        mc.getOpponent().setText(client[1]);
                        mc.getYou().setText(Long.toString(mc.getMeasuredTime()));
                    } else if (mc.getMeasuredTime() < Integer.getInteger(client[1])) {
                        mc.getO_win().setVisible(true);
                        mc.getY_lose().setVisible(true);
                        mc.getOpponent().setText(client[1]);
                        mc.getYou().setText(Long.toString(mc.getMeasuredTime()));
                    } else if (mc.getMeasuredTime() == Integer.getInteger(client[1])) {
                        mc.getO_win().setVisible(true);
                        mc.getY_win().setVisible(true);
                        mc.getOpponent().setText(client[1]);
                        mc.getYou().setText(Long.toString(mc.getMeasuredTime()));
                    }

                    if (msgFromClient.equalsIgnoreCase("BYE"))
                        break;
                    msgFromClient = "";
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
            } catch (IOException e){
                e.printStackTrace();
            }

        }
    }
}
