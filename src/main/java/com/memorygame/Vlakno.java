package com.memorygame;

import java.io.IOException;

public class Vlakno extends  Thread{

    String role;
    String cport;
    int sport;

    public Vlakno(String role, String cport, int sport) {
        this.role = role;
        this.cport = cport;
        this.sport = sport;
    }

    public void run() {
        try {
            switch (role) {
                case "server":
                    se(sport);
                    break;

                case "client":
                    System.out.println("Client");
                    cl(cport);

                    break;
            }
        }catch (IOException e) {
            System.out.println("Chyba");
        }
    }
    public void se (int port) throws IOException{
        Server s = new Server();
        s.server(port);
    }

    public void cl (String port)  {
        Client c = new Client();
        c.client(port);
    }
}
