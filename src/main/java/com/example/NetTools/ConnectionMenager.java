package com.example.NetTools;

import com.example.Meat.Demo.OnlineGame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ConnectionMenager {

    private static final int PORT_NUMBER = 3571;

    //awaryjne pola do przechwycenia wyniku w razie rozłączenia się, któregoś z graczy
    public static float PLAYER_ONE_POINTS = 0;
    public static float PLAYER_TWO_POINTS = 0;


/*
    public WYNIK_Z_BAZY search(){
        //funkcja ma za zadanie pobrać z tabeli np. 'połączenia' wyniki i je zwrócić
        return WYNIK_Z_BAZY;
    }
*/

    //rozgrywka w trybie serwera
    public static void server() {
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
            OnlineGame game = new OnlineGame(serverSocket, clientSocket, out, PLAYER_ONE_POINTS, PLAYER_TWO_POINTS);

            //naprzemienne tury graczy
            while (true) {
                //gracz, który jest serwerem rozgrywa turę jako pierwszy
                game.server_turn(in);
                //gracz, który jest klientem rozgrywa turę jako pierwszy
                game.client_turn(in);
            }

        } catch (IOException e) {   //obsługa zdarzenia gdy klient/oponent się rozłączy

            System.out.println("Przeciwnik rozłączył się");
            System.out.println("\n" + "\n" + "\n" + "\n" + "\n"  + "\n" + "\n" + "\n" + "\n" + "\n");
            System.out.println("GRA SKONCZONA!");
            //rozdzielne są punkty tak jak gdyby gracz 1 wygrał grę
            //wykorzystywane są pola na awaryjne punkty
            PLAYER_ONE_POINTS += 2.0;
            PLAYER_TWO_POINTS -= 0.5;
            System.out.println("Gracz 1 " + PLAYER_ONE_POINTS + ":" + PLAYER_TWO_POINTS + " Gracz 2");
        /*
        TU NALEŻY WSTAWIĆ FRAGEMNT KODU WYSYŁAJĄCY WYNIKI DO BAZY
        MOŻE NP ŚCIĄGNĄĆ TYCH GRACZY Z TABELI WYNIKI, DODAĆ IM PUNKTY Z TEJ GRY I ZUPDATOWAĆ REKORDY W BAZIE
         */
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
            //klient cały czas nasłuchuje, czyta...
            while ((fromServer = in.readLine()) != null) {
                //... i przetwarza wiadomości od serwera
                toServer = turnService(fromServer);
                //jeżeli przetworzona wiadomość jest taka sama jak po jej otrzymaniu, to po prostu ją wyświetl
                if(toServer.equals(fromServer))
                    System.out.println(fromServer);
                //jeżeli serwer zamyka połączenie, bo gra jest skończona, to przerwij nasłuchiwanie
                else if(toServer.equals("CONNECTION_CLOSE"))
                    break;
                //w innym przypadku odeślij do serwera odpowiedź
                else
                    out.println(toServer);
            }

        } catch (UnknownHostException e) {  //obsługa błędnego ip

            System.err.println("Don't know about host " + host_ip);
            System.exit(1);
        } catch (IOException e) {   //obsługa zdarzenia gdy serwer/gracz 1 się rozłączy

            System.out.println("Przeciwnik rozłączył się");
            System.out.println("\n" + "\n" + "\n" + "\n" + "\n"  + "\n" + "\n" + "\n" + "\n" + "\n");
            System.out.println("GRA SKONCZONA!");
            //rozdzielne są punkty tak jak gdyby gracz 2 wygrał grę
            //wykorzystywane są pola na awaryjne punkty
            PLAYER_TWO_POINTS += 2.0;
            PLAYER_ONE_POINTS -= 0.5;
            System.out.println("Gracz 1 " + PLAYER_ONE_POINTS + ":" + PLAYER_TWO_POINTS + " Gracz 2");

            //jest to jedyny przypadek gdy klient musi obsłużyć wynik gry

            /*
        TU NALEŻY WSTAWIĆ FRAGEMNT KODU WYSYŁAJĄCY WYNIKI DO BAZY
        MOŻE NP ŚCIĄGNĄĆ TYCH GRACZY Z TABELI WYNIKI, DODAĆ IM PUNKTY Z TEJ GRY I ZUPDATOWAĆ REKORDY W BAZIE
         */
        }
    }


    //usługa, z której korzysta klient
    //ma za zadanie przetwarzać wejściowe napisy i zwracać wyjściowe
    private static String turnService(String in) throws IOException {
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
}
