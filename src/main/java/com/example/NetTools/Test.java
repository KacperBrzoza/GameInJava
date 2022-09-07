package com.example.NetTools;

import java.io.IOException;
import java.net.ServerSocket;

public class Test extends Thread{

    private Server server;

    public Test(Server server){
        this.server = server;
    }

    @Override
    public void run() {
        try {
            server = new Server(new ServerSocket(3571));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
