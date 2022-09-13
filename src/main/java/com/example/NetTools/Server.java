package com.example.NetTools;

import com.example.Main.Game.GameController;
import com.example.Main.Login.Memory;
import com.example.Meat.Demo.OnlineGame;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    //awaryjne pola do przechwycenia wyniku w razie rozłączenia się, któregoś z graczy
    public static float PLAYER_ONE_POINTS = 0;
    public static float PLAYER_TWO_POINTS = 0;

    private ServerSocket serverSocket;
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private OnlineGame newGame;
    private String opponentNick;

    public Server(ServerSocket serverSocket) {
        try{
            this.serverSocket = serverSocket;
            System.out.println("gniazdko");

            //if(WaitingEnemyController.zmiennaAccept) {
                this.socket = serverSocket.accept();
                System.out.println("polaczono");
                this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            //}
        } catch (IOException e){
            System.out.println("Error creating server.");
            e.printStackTrace();
        }
    }

    public void startGame(GameController gameController){
        //inicjalizacja gry online
        newGame = new OnlineGame(serverSocket, socket, bufferedWriter, PLAYER_ONE_POINTS, PLAYER_TWO_POINTS, gameController);
    }

    public void sendAndListen(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    bufferedWriter.write(Memory.memory.getUsername());
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                } catch (IOException e){
                    e.printStackTrace();
                    System.out.println("Error sending message to the client");
                    closeEverything();
                }
                while (socket.isConnected()){
                    try {
                        String messageFromClient = bufferedReader.readLine();
                        GameController.opponentNick = messageFromClient;
                        System.out.println(messageFromClient);
                        break;
                    } catch (IOException e){
                        e.printStackTrace();
                        System.out.println("Error receiving message from the client");
                        closeEverything();
                        break;
                    }
                }
            }
        }).start();
    }



    public String waitForClientChoice() throws IOException{
        String choice = "-1";
        while (socket.isConnected()){
            try {
                choice = bufferedReader.readLine();
                break;
            } catch (IOException e){
                //e.printStackTrace();
                System.out.println("LEVEL -1");
                choice = "9999";
                closeEverything();
                break;
            }
        }
        return choice;
    }

    public void sendMessageToClient(String messageFromServer){
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try{
                    //do kazdej wiadomosci doklejany jest z przodu znak konca linii, aby komendy nie skleily sie ze soba
                    bufferedWriter.write("\n" + messageFromServer);
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                } catch (IOException e){
                    closeEverything();
                }
            }
        }).start();
    }


    public void turns(GameController gameController){
        new Thread(new Runnable() {
            @Override
            public void run() {
                //connectionGuardian(gameController);
                while (socket.isConnected()){
                    try {
                        newGame.server_turn(bufferedReader, gameController);    //gracz, który jest serwerem rozgrywa turę jako pierwszy
                        newGame.client_turn(bufferedReader, gameController);    //gracz, który jest klientem rozgrywa turę jako drugi
                    } catch (IOException e) {
                        System.err.println("LEVEL 2");
                        GameController.connectionClose(gameController.ChoiceHBox, gameController.EndGameLabel, gameController.PointsLabel, gameController.ExitButton);
                        closeEverything();
                        //break;
                    }
                }
            }
        }).start();
    }
    

    public void closeEverything(){
        try{
            if(bufferedReader != null){
                bufferedReader.close();
                //System.out.println("Zamkniecie readera servera");
            }
            if(bufferedWriter != null){
                bufferedWriter.close();
                //System.out.println("Zamkniecie writera servera");
            }
            if (serverSocket != null){
                serverSocket.close();
                //System.out.println("Zamkniecie server socketa ");
            }
            if(socket != null){
                socket.close();
                //System.out.println("Zamkniecie socketa");
            }
            //System.out.println("Polaczenie zamkniete");
        } catch (IOException e){
            e.printStackTrace();
            System.out.println("BLAD SOCKET");
        }

    }

    public void closeConnection() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //do kazdej wiadomosci doklejany jest z przodu znak konca linii, aby komendy nie skleily sie ze soba
                    bufferedWriter.write("\n" + "LAST_MESSAGE");
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                    if (bufferedReader != null) {
                        bufferedReader.close();
                        //System.out.println("Zamkniecie readera servera");
                    }
                    if (bufferedWriter != null) {
                        bufferedWriter.close();
                        //System.out.println("Zamkniecie writera servera");
                    }
                    if (serverSocket != null) {
                        serverSocket.close();
                        //System.out.println("Zamkniecie server socketa ");
                    }
                    if (socket != null) {
                        socket.close();
                        //System.out.println("Zamkniecie socketa");
                    }
                    //System.out.println("Polaczenie zamkniete");
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("BLAD SOCKET");
                }
            }
        }).start();
    }
}
