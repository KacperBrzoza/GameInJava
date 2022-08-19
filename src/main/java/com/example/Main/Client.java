package com.example.Main;

import com.example.NetTools.ConnectionMenager;

import java.net.InetAddress;
import java.net.UnknownHostException;


//klient tylko do testów
public class Client {
    public static void main(String [] args) throws UnknownHostException {

        //DO TESTOWANIA NA LOOPBACKU
        //InetAddress inetAddress = InetAddress.getLocalHost();
        //String ip = inetAddress.getHostAddress();
        //System.out.println(ip);
        //ConnectionMenager.client(ip);


        //wpisz na razie na sztywno adres ip serwera
        ConnectionMenager.client("192.168.0.30");
    }
}
