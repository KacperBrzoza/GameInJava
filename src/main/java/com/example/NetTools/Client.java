package com.example.NetTools;

import com.example.Main.Game.GameController;
import com.example.Main.Login.Memory;
import javafx.application.Platform;

import java.io.*;
import java.net.Socket;

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

    public void listenAndSend(String messageToServer){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (socket.isConnected()){
                    try {
                        String messageFromServer = bufferedReader.readLine();
                        GameController.opponentNick = messageFromServer;
                        //System.out.println(messageFromServer);
                        break;
                    } catch (IOException e){
                        e.printStackTrace();
                        System.out.println("Error receiving message from the client");
                        closeEverything();
                        break;
                    }
                }
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
        }).start();
    }


    public void turns(GameController gameController){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String fromServer;
                String toServer;
                while (socket.isConnected()){
                    try {
                        fromServer = bufferedReader.readLine();
                        //... i przetwarza wiadomości od serwera
                        toServer = turnService(fromServer, gameController);
                        //jeżeli przetworzona wiadomość jest taka sama jak po jej otrzymaniu, to po prostu ją wyświetl
                        if (toServer.equals(fromServer))
                            System.out.println(fromServer);
                            //jeżeli serwer zamyka połączenie, bo gra jest skończona, to przerwij nasłuchiwanie
                        else if (toServer.equals("CONNECTION_CLOSE"))
                        {
                            break;
                        }
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
    private String turnService(String in, GameController gameController) throws IOException {
        String out = "";        //poczatkowo wyjscie jest pustym napisem
        String val;
        if (Commands.yourTurn(in)) {
            GameController.changeTurn(gameController.EndTurnButton, gameController.TakeCardDeck, gameController.RageCardDeck, gameController.MoneyStack, gameController.LostCardDeck, gameController.CardCounter);
        }
        else if(Commands.endGame(in)) {
            System.out.println("SEEE YAAA");
            out = "CONNECTION_CLOSE";
        }
        else if(Commands.unableRightShowBut(in)){
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    gameController.RightShowBut.setDisable(false);
                }
            });
        }
        else if (Commands.selectingPhase(in)) {
            GameController.selectingPhase(gameController.TakeCardDeckSelect, gameController.MoneyStackSelect, gameController.EndTurnButton);
        }
        else if (Commands.showEQ(in)){
            GameController.showEQ(gameController.eq_it, gameController.eqImages, gameController.EQ1, gameController.EQ2, gameController.EQ3, gameController.EQ4);
        }
        else if (Commands.showBattleField(in)) {
            GameController.showBattleField(gameController.fields, gameController.mygrid0, gameController.mygrid1, gameController.mygrid2, gameController.mygrid3, gameController.mygrid4, gameController.enemygrid0, gameController.enemygrid1, gameController.enemygrid2, gameController.enemygrid3, gameController.enemygrid4);
        }
        else if (Commands.phase(in) != -1) {
            GameController.phase = Commands.phase(in);
        }
        else if (Commands.choice(in) != -100){
            GameController.choice = Commands.choice(in);
        }
        else if (!(val = Commands.newCardStackSize(in)).equals("-1")) {
            GameController.newLabelValue(gameController.CardCounter, val);
        }
        else if (!(val = Commands.newMyMoneyVal(in)).equals("-1")) {
            GameController.newLabelValue(gameController.MoneyPlayerValue, val);
        }
        else if (!(val = Commands.setField(in, gameController)).equals("-1")) {
            //wyjatkowo dzialanie tego ifa zamieszczone jest w komendzie
        }
        else if (!(val = Commands.path(in)).equals("-1")) {
            GameController.addImageToEQ(gameController.eqImages, val);
        }

        /*
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

         */
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
        }).start();

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
                System.out.println("Zamkniecie readera clienta");
            }
            if(bufferedWriter != null){
                bufferedWriter.close();
                System.out.println("Zamkniecie writera clienta");
            }
            if(socket != null){
                socket.close();
                System.out.println("Zamkniecie socketa clienta");
            }
            //System.out.println("Polaczenie zamkniete");
        } catch (IOException e){
            e.printStackTrace();
        }

    }
}
