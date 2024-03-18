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
        switch (role) {
            case "client":
                cl(cport);
                break;
            case "server":
                try {
                    se(sport);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
        }
    }
    public void cl (String port) {
        Client c = new Client();
        c.client(port);
    }

    public void se (int port) throws IOException {
        Server s = new Server();
        s.server(port);
    }
}
