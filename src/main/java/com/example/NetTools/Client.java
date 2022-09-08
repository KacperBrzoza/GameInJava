package com.example.NetTools;

import com.example.Main.Game.GameController;
import com.example.Main.Login.Memory;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;


    public Client(Socket socket){
        try {
            this.socket = socket;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            System.out.println("polaczylem");
        } catch (IOException e){
            System.out.println("Error creating client.");
            e.printStackTrace();
            closeEverything();
        }
    }

    public void listenAndSend(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (socket.isConnected()){
                    try {
                        String messageFromServer = bufferedReader.readLine();
                        GameController.opponentNick = messageFromServer;
                        break;
                    } catch (IOException e){
                        e.printStackTrace();
                        System.out.println("Error receiving message from the client");
                        closeEverything();
                        break;
                    }
                }
                try{
                    bufferedWriter.write(Memory.memory.getUsername());
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                } catch (IOException e){
                    e.printStackTrace();
                    System.out.println("Error sending message to the client");
                    closeEverything();
                }
            }
        }).start();
    }


    public void turns(Label EQLabel){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String fromServer;
                String toServer;
                while (socket.isConnected()){
                    try {
                        fromServer = bufferedReader.readLine();
                        //... i przetwarza wiadomości od serwera
                        toServer = turnService(fromServer, EQLabel);
                        //jeżeli przetworzona wiadomość jest taka sama jak po jej otrzymaniu, to po prostu ją wyświetl
                        if (toServer.equals(fromServer))
                            System.out.println(fromServer);
                            //jeżeli serwer zamyka połączenie, bo gra jest skończona, to przerwij nasłuchiwanie
                        else if (toServer.equals("CONNECTION_CLOSE"))
                            break;
                        //w innym przypadku odeślij do serwera odpowiedź
                        //else
                        //out.println(toServer);
                    } catch (IOException e){
                        e.printStackTrace();
                        System.out.println("Error receiving message from the server");
                        closeEverything();
                        break;
                    }
                }
            }
        }).start();
    }

    //usługa, z której korzysta klient
    //ma za zadanie przetwarzać wejściowe napisy i zwracać wyjściowe
    private String turnService(String in, Label EQLabel) throws IOException {
        String out = "";        //poczatkowo wyjscie jest pustym napisem

        //gdy wejściem jest sygnał poniżej, zmusza klienta do wybrania liczby 1 lub 2
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

        else if(in.equals("COMMAND_01")){
            GameController.showMessage("AAAAAAAAAAAAAAAA", EQLabel);
        }

        //gdy wejściem jest sygnał poniżej, zmusza klienta do wybrania liczby z zakresu od 1 do 5
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

        //gdy wejściem jest krótki, niepusty napis (PEWNIE JAKAŚ LICZBA), zmusza klienta do wybrania liczby z zakresu od 0 do (TEJ) liczby
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

        //gdy wejściem jest sygnał poniżej, zwraca informację do zamknięcia połączenia i dalszych czynności
        else if(in.equals("END_GAME")){
            out = "CONNECTION_CLOSE";
        }

        //w innych przypadkch wejście staje się wyjściem
        else
            out = in;

        return out;
    }

    public void sendMessageToServer(String messageToServer){
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try{
                    bufferedWriter.write(messageToServer);
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                } catch (IOException e){
                    e.printStackTrace();
                    System.out.println("Error sending message to the client");
                    closeEverything();
                }
            }
        });

    }

    /*
    public void receiveMessageFromServer(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Czekam na wiadomosci");
                while (socket.isConnected()){
                    try {
                        System.out.println("zobacze jakie dostane");
                        String messageFromServer = bufferedReader.readLine();
                        System.out.println(messageFromServer);

                        //HelloController.addLabel(messageFromServer, vBox);
                    } catch (IOException e){
                        e.printStackTrace();
                        System.out.println("Error receiving message from the server");
                        closeEverything(socket, bufferedReader, bufferedWriter);
                        break;
                    }
                }
            }
        }).start();
    }*/

    public void closeEverything(){
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
        System.out.println("Polaczenie zamkniete");
    }
}
