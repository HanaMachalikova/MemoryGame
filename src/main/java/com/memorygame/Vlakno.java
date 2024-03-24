package com.memorygame;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Vlakno extends  Thread{

    String role;
    String cport;
    int sport;
    Boolean ready;

    public Vlakno(String role, String cport, int sport, boolean ready) {
        this.role = role;
        this.cport = cport;
        this.sport = sport;
        this.ready = ready;
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
                    String msgFromClient = bufferedReader.readLine();
                    if(msgFromClient.equalsIgnoreCase("ready")) {
                        ready = true;
                        System.out.println("ready to play");
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

            while (true) {
                String msgToSend = sc.nextLine();
                bufferedWriter.write(msgToSend);
                bufferedWriter.newLine();
                bufferedWriter.flush();

                System.out.println("Client: " + bufferedReader.readLine());

                if(msgToSend.equalsIgnoreCase("BYE"))
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
            } catch (IOException e){
                e.printStackTrace();
            }

        }
    }
}
