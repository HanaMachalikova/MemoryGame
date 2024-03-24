package com.memorygame;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    Boolean ready = false;
    void server(int portNumber) throws IOException {
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
}
