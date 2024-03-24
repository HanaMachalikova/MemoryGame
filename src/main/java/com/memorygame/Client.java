package com.memorygame;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
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

            Scanner sc = new Scanner(System.in);
            System.out.println("Client is ready");
            String ready = "ready";
            bufferedWriter.write(ready);
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
