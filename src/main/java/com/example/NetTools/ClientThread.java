package com.example.NetTools;

public class ClientThread extends Thread{

    private String ip;

    public ClientThread(String ip){
        this.ip = ip;
    }

    @Override
    public void run() {
        ConnectionMenager.client(ip);
    }
}
