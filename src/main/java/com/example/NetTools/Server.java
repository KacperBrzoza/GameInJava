package com.example.NetTools;

import com.example.Meat.Demo.OnlineGame;
import javafx.scene.layout.VBox;

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

    public Server(ServerSocket serverSocket) {
        try{
            this.serverSocket = serverSocket;
            this.socket = serverSocket.accept();
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e){
            System.out.println("Error creating server.");
            e.printStackTrace();
        }
    }

    public void startGame(){
        //inicjalizacja gry online
        newGame = new OnlineGame(serverSocket, socket, bufferedWriter, PLAYER_ONE_POINTS, PLAYER_TWO_POINTS);
    }

    public void turns(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (socket.isConnected()){
                    //gracz, który jest serwerem rozgrywa turę jako pierwszy
                    try {
                        newGame.server_turn(bufferedReader);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //gracz, który jest klientem rozgrywa turę jako pierwszy
                    try {
                        newGame.client_turn(bufferedReader);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public void sendMessageToClient(String messageToServer){
        try{
            bufferedWriter.write(messageToServer);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e){
            e.printStackTrace();
            System.out.println("Error sending message to the client");
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public void receiveMessageFromClient(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (socket.isConnected()){
                    try {
                        String messageFromClient = bufferedReader.readLine();
                        System.out.println(messageFromClient);
                        //HelloController.addLabel(messageFromClient, vbox);
                    } catch (IOException e){
                        e.printStackTrace();
                        System.out.println("Error receiving message from the client");
                        closeEverything(socket, bufferedReader, bufferedWriter);
                        break;
                    }
                }
            }
        }).start();
    }

    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter){
        try{
            if(bufferedReader != null){
                bufferedReader.close();
            }
            if(bufferedWriter != null){
                bufferedWriter.close();
            }
            if(socket != null){
                socket.close();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
