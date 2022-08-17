package com.example.NetTools;

import com.example.Meat.Demo.OnlineGame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class ConnectionMenager {

    private static final int SWITCHER = 0;
    private static final int PORT_NUMBER = 3571;

    public static void main(String [] args) throws UnknownHostException {
        if(SWITCHER == 0){
            server();
        }
        else{
            client("192.168.0.25");
            //client(zdobyte ip z bazy z tabeli polaczenia);
        }
    }

/*
    public WYNIK_Z_BAZY search(){
        //funkcja ma za zadanie pobrać z tabeli np. 'połączenia' wyniki i je zwrócić
        return WYNIK_Z_BAZY;
    }
*/

    public static void server() throws UnknownHostException {

        System.out.println("[ SERWER ]: Oczekiwanie na przeciwnika...");
        try(
                //utworzenie gniazda serwera oraz...
                ServerSocket serverSocket = new ServerSocket(PORT_NUMBER);
                Socket clientSocket = serverSocket.accept();

                //...strumieni do czytania i wysyłania danych przez sieć
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
                ){

            //od tego miejsca rzeczy dzieją się gdy ktoś się połączy, tzn gdy oponent zostanie znaleziony
            System.out.println("[ SERWER ]: Przeciwnik polaczyl sie...");

            //inicjalizacja gry online
            OnlineGame game = new OnlineGame();

            //gracz, który jest serwerem rozgrywa grę jako pierwszy
            game.server_turn(game.you, game.opponent, out, in);

            //zamykanie gniazda na koniec działania
            serverSocket.close();
            clientSocket.close();

        } catch (IOException e) {
        System.out.println("Exception caught when trying to listen on port "
                + PORT_NUMBER + " or listening for a connection");
        System.out.println(e.getMessage());
        }
    }

    public static void client(String host_ip){
        try (
                //klient tworzy gniazdo i strumienie
                Socket socket = new Socket(host_ip, PORT_NUMBER);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()))
        ) {

            String fromServer;

            while ((fromServer = in.readLine()) != null) {
                System.out.println(fromServer);
            }
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + host_ip);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                    host_ip);
            System.exit(1);
        }
    }

}
