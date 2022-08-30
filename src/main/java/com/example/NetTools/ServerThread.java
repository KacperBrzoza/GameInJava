package com.example.NetTools;

public class ServerThread extends Thread{

    @Override
    public void run() {
        ConnectionMenager.server();
    }
}
