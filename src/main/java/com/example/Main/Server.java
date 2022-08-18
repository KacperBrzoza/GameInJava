package com.example.Main;

import com.example.NetTools.ConnectionMenager;

import java.net.UnknownHostException;


//serwer tylko do testów
public class Server {
    public static void main(String [] args) throws UnknownHostException {

            ConnectionMenager.server();
    }
}
