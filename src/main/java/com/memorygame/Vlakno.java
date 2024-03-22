package com.memorygame;

import java.io.IOException;

public class Vlakno extends  Thread{

    String role;
    int cport;
    String sport;

    public Vlakno(String role, int cport, String sport) {
        this.role = role;
        this.cport = cport;
        this.sport = sport;
    }

    public void run() {
        try {
            switch (role) {
                case "client":
                    cl(cport);
                    break;
                case "server":
                        se(sport);
                    break;
            }
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void se (String port) {
        Server s = new Server();
        s.server(port);
    }

    public void cl (int port) throws IOException {
        Klient c = new Klient();
        c.client(port);
    }
}
