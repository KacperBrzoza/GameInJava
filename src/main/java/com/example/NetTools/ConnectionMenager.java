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
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ConnectionMenager {

    private static final int PORT_NUMBER = 3571;


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
            OnlineGame game = new OnlineGame(serverSocket, clientSocket);

            while (true) {
                //gracz, który jest serwerem rozgrywa turę jako pierwszy
                game.server_turn(out, in);
                //gracz, który jest klientem rozgrywa turę jako pierwszy
                game.client_turn(out, in);
            }

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
            String toServer;
            System.out.println("TURA PRZECIWNIKA");
            while ((fromServer = in.readLine()) != null) {
                toServer = turnService(fromServer);
                if(toServer.equals(fromServer))
                    System.out.println(fromServer);
                else if(toServer.equals("CONNECTION_CLOSE"))
                    break;
                else
                    out.println(toServer);
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


    private static String turnService(String in) throws IOException {
        String out = "";
        if(in.equals("ONE_OR_TWO")){
            Scanner scan = new Scanner(System.in);
            int number = -1;
            while (number == -1){
                System.out.print("wybierz: ");
                number = scan.nextInt();
                if(number != 1 && number != 2)
                    number = -1;
            }
            out += number;
        }
        else if(in.equals("CHOICE")){
            Scanner scan = new Scanner(System.in);
            int number = -1;
            while (number == -1){
                System.out.print("wybierz: ");
                number = scan.nextInt();
                if(number < 1 || number > 5)
                    number = -1;
            }
            out += number;
        }
        else if(in.length() <= 3 && !in.equals("")){
            Scanner scan = new Scanner(System.in);
            int size = Integer.parseInt(in);
            int number = 100000;
            while (number < 0 || number > size){
                System.out.print("wybierz: ");
                number = scan.nextInt();
                if(number < 0 || number > size)
                    number = -1;
            }
            if(number == size)
                out += "10000";
            else
                out += number;
        }
        else if(in.equals("END_GAME")){
            out = "CONNECTION_CLOSE";
        }
        else
            out = in;
        return out;
    }
}
