package com.example.Meat.Demo;

import com.example.Main.Game.GameController;
import com.example.Meat.Creatures.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class OnlineGame {
    public Player you;                 //gracz 1
    public Player opponent;            //gracz 2
    public Cards_Stack cards;          //zakryty "stos" kart stworów
    public Discardeds_Stack discarded; //odkryty stos kart odrzuconych stworów
    public Money money;                //zakryty "stos" żetonów waluty
    public static Board board;                //plansza
    public Rage_Cards rage_cards;      //zakryty "stos" kart Rage

    public ServerSocket serverSocket;  //gniazdo serwera
    public Socket clientSocket;        //gniazdo klienta
    public BufferedWriter out;            //strumień do wysyłania rzeczy do klienta

    //awaryjne pola do przechwycenia wyniku w razie rozłącznia, któregoś z graczy
    public float  PLAYER_ONE_POINTS;
    public float  PLAYER_TWO_POINTS;

    public OnlineGame(ServerSocket serverSocket, Socket clientSocket, BufferedWriter out, float PLAYER_ONE_POINTS, float PLAYER_TWO_POINTS, GameController gameController){
        you = new Player(1);
        opponent = new Player(2);

        cards = new Cards_Stack();
        discarded = new Discardeds_Stack();
        money = new Money();

        rage_cards = new Rage_Cards();
        rage_cards.initialization();

        this.serverSocket = serverSocket;
        this.clientSocket = clientSocket;
        this.out = out;

        this.PLAYER_ONE_POINTS = PLAYER_ONE_POINTS;
        this.PLAYER_TWO_POINTS = PLAYER_TWO_POINTS;

        board = new Board(this.PLAYER_ONE_POINTS, this.PLAYER_TWO_POINTS);

        startGame(you, opponent, gameController);
        System.out.println("OK");
    }


    //początkowe zasoby dla każdego gracza (3 żetony waluty i 3 karty stworów)
    private void startGame(Player you, Player opponent, GameController gameController){
        for(int i = 0; i < 6; i++){
            Creature creature = cards.giveCard(out, you, opponent, gameController);
            opponent.eq.addCreature(creature);
            GameController.server.sendMessageToClient("PATH_" + creature.path);

             creature = cards.giveCard(out, you, opponent, gameController);
            you.eq.addCreature(creature);                                                                       //dodanie stwora do eq gracza 1
            GameController.addImageToEQ(gameController.eqImages, creature.path);                                                 //dodanie zdjecia stwora do listy od eq


            you.money += money.giveMoney(you, opponent);
            opponent.money += money.giveMoney(you, opponent);

        }
        GameController.server.sendStartSet("NEW_CARDS_STACK_SIZE_" + cards.size(), "NEW_MY_MONEY_VAL_" + opponent.money, "SHOW_EQ");
        GameController.newNumberValue(gameController.CardCounter, "" + cards.size());
        GameController.newNumberValue(gameController.MoneyPlayerValue, "" + you.money);
        GameController.showEQ(gameController.eq_it, gameController.eqImages, gameController.EQ1, gameController.EQ2, gameController.EQ3, gameController.EQ4);
    }


    //METODY DLA GRACZA, KTÓRY JEST SERWEREM

    //tura pierwszego gracza
    public void server_turn(BufferedReader in, GameController gameController) throws IOException {


        //1. przejścia stworów w stronę bazy przeciwnika
        board.move(you, opponent, discarded, cards,  rage_cards, money, out, in, gameController);
        
        //wysłanie planszy do przeciwnika
        //out.println(board);
        //GameController.server.sendMessageToClient(board.toString());

        System.out.println(this);
        System.out.println(board);
        you.showMoney();

        //2. dobrania kart stworów lub żetonów waluty. Gracz ma dwa dobrania
        draw(gameController);

        //3.Jeżeli gracz ma coś w ekwipunku...
        if(you.eq.size() >= 1) {
            Scanner scan = new Scanner(System.in);
            int number = 2;
            //3. ... i ma kartę Rage "Black Market" może...
            if (you.BlackMarket == 1) {
                number = -1;
                System.out.println("Mozesz sprzedac JEDNEGO stwora i zakonczyc ture lub wystawiac karty:");
                System.out.println("(1) sprzedaj");
                System.out.println("(2) wystaw");
                while (number < 1 || number > 2) {
                    System.out.print("wybierz: ");
                    number = scan.nextInt();
                }
                //... sprzedawać lub...
                if (number == 1)
                    sell();
            }
            //... może wystawiać tyle stworów na ile stać gracza, o ile jakieś ma, przestrzegając limitu 4 swoich stworów na planszy
            if (number == 2)
                display(in, gameController);
        }

        //4. Jeśli gracz posiada kartę Rage "Secret Assets" dostaje 1 żeton waluty na koniec tury
        if(you.SecretAssets == 1){
            you.money += money.giveMoney(you, opponent);
            System.out.println("Dobrano zeton waluty");
        }

        //4. Jeśli gracz posiada kartę Rage "RatCatcher" dostaje 1 kartę stwora na koniec tury
        if(you.RatCatcher == 1){
            you.eq.addCreature(cards.giveCard(out, you, opponent, gameController));
            System.out.println("Dobrano stwora");
        }

        System.out.println("\n\n\nTURA PRZECIWNIKA");
    }

    //pozwala pierwszemu graczowi dobrać 2x żeton waluty  lub  2x kartę stwora  lub  1x to i 1x to
    private void draw(GameController gameController){
        you.select = 2;   //liczba dobrań

        while(you.select > 0){
            Scanner scan =  new Scanner(System.in);
            System.out.println("1 - dobierz karte (lub) 2 - dobierz zeton waluty");
            System.out.print("wybierz: ");
            int number = scan.nextInt();

            //dobranie karty
            if(number == 1){
                Creature one = cards.giveCard(out, you, opponent, gameController);
                //jeżeli gracz posiada kartę Rage "Selection" dobiera dwie karty. Jedną zatrzymuje, drugą odrzuca
                if(you.Selection == 1){
                    Creature two = cards.giveCard(out, you, opponent, gameController);
                    System.out.println("(1) " + one);
                    System.out.println("(2) " + two);
                    number = -1;
                    while (number < 1 || number > 2){
                        System.out.print("wybierz: ");
                        number = scan.nextInt();
                    }
                    if(number == 1)
                        you.eq.addCreature(one);
                    else
                        you.eq.addCreature(two);
                }
                else{
                    System.out.println(one);
                    you.eq.addCreature(one);
                }
                you.select--;
            }

            //dobranie żetonu waluty
            else if(number == 2){
                you.select--;
                int coin_one = money.giveMoney(you, opponent);
                //jeżeli gracz posiada kartę Rage "Second Chance" dobiera dwa żetony waluty. Jedną zatrzymuje, drugą odrzuca
                if(you.SecondChance == 1){
                    int coin_two = money.giveMoney(you, opponent);
                    System.out.println("(1) " + coin_one);
                    System.out.println("(2) " + coin_two);
                    number = -1;
                    while (number < 1 || number > 2){
                        System.out.print("wybierz: ");
                        number = scan.nextInt();
                    }
                    if(number == 1)
                        you.money += coin_one;
                    else
                        you.money += coin_two;
                }
                else {
                    System.out.println(coin_one);
                    you.money += coin_one;
                }
            }
            System.out.println("Pozostale ruchy: " + you.select);
        }
    }

    //metoda odpowiadająca za wystawianie kart i dodatkowe akcje
    private void display(BufferedReader in, GameController gameController) throws IOException {
        if(you.counter < 4)
            while (you.counter < 4 ){
                System.out.println("Pieniadze: " + you.money);
                System.out.println("1 - wybierz karte \n 2 - podejrzyj plansze \n 3 - spasuj \n 4 - obejrzyj swoje karty Rage \n 5 - legenda mocy stworow");

                Scanner scan = new Scanner(System.in);
                System.out.println("wybierz: ");
                int number = scan.nextInt();
                //wystawienie karty
                if(number == 1){
                    System.out.println("\nTwoj ekwpiunek:");
                    System.out.println(you.eq);
                    System.out.println("(" + you.eq.size() + ") cofnij");
                    you.showMoney();

                    number = -1;
                    while (number < 0 || number > you.eq.size()){
                        System.out.print("wybierz: ");
                        number = scan.nextInt();
                        if(number >= 0 && number < you.eq.size()){
                            if(you.eq.checkCost(number) <= you.money) {
                                you.money -= you.eq.checkCost(number);
                                Creature creature = you.eq.pickCreature(number);
                                //zwiększenie ataku stwora w przypadku posiadania karty Rage "Swarm"
                                if(you.Swarm == 1){
                                    if(creature.getAttack() == 2) {
                                        creature.increaseAttack();
                                        creature.setSwarm(1);
                                    }
                                }
                                //zwiększenie hp stwora w przypadku posiadania karty Rage "Unbroaken"
                                if(you.Unbroaken == 1){
                                    if(creature.getHp() == 2) {
                                        creature.increaseHp();
                                        creature.setUnbroaken(1);
                                    }
                                }
                                board.put(creature, you, opponent, discarded);
                                you.counter++;
                                creature.effect(you, opponent, cards, discarded, money, board, out, in, gameController);
                                System.out.println("\n" + board);
                                //out.println("\n" + board);
                            }
                            else {
                                System.out.println("Ta jednostka jest za droga!");
                                number = -1;
                            }
                        }
                        else if(number == you.eq.size())
                            break;
                    }
                }
                //podejrzenie planszy
                else if(number == 2){
                    System.out.println(this);
                    System.out.println(board);
                }
                //pass
                else if(number == 3){
                    break;
                }
                //podejrzenie swoich kart Rage
                else if(number == 4){
                    System.out.println(you.rage);
                }
                //legenda mocy
                else if(number == 5){
                    System.out.println();
                    System.out.println("D - przy wystawieniu dobierasz karte stwora");
                    System.out.println("E - przy wystawieniu pozwala natychmiast wystawic kolejnego stwora z moca E za darmo");
                    System.out.println("F - jesli na przeciwnym polu nie ma wroga, probuje walczyc z przeciwnikiem o 1 pole dalszym");
                    System.out.println("G - odsyla pierwszego napotkanego przeciwnika spowrotem do ekwipunku oponenta");
                    System.out.println("H - przy wystawieniu uzdrawia jednostke, tzn dobierasz karte stwora ze stosu kart odrzuconych");
                    System.out.println("J - gdy ta jednostka ginie, zmienia hp swojego zabojcy na 1");
                    System.out.println("M - przy wystawieniu dobierasz zeton waluty");
                    System.out.println("N - gdy ta jednostka ma zostac zabita, jednorazowo unika smierci");
                    System.out.println("O - pozwala natychmiast wystawic kolejnego stwora za darmo");
                    System.out.println("R - gdy sojusznik ma zginac, ta jednostka zginie zamiast tamtej");
                    System.out.println("U - sprawia, ze jednostki na dwoch polach za nia sa niezniszczalne, dopoki sama zyje");
                    System.out.println("X - zabija pierwszego napotkanego przeciwnika bez wzgledu na statystyki");
                    System.out.println("Z - przy wystawieniu pozwala zamienic miejscami ta jednostke z inna sojusznicza");
                    System.out.println();
                }
            }
        else
            System.out.println("Osiagnieto limit 4 kart na planszy!");
    }

    //służy do sprzedawania stworów jeżeli gracz pierwszy posiada kartę "Black Market"
    private void sell(){
        System.out.println("Wybierz jednostke do sprzedania:");
        System.out.println(you.eq);
        Scanner scan = new Scanner(System.in);
        int number = -1;
        while (number < 0 || number >= you.eq.size()){
            System.out.print("Wybierz: ");
            number = scan.nextInt();
            if(number >= 0 && number < you.eq.size()){
                Creature creature = you.eq.pickCreature(number);
                you.money += creature.getCost();
                discarded.putCard(creature);
                break;
            }
        }
        System.out.println("Sprzedano stwora");
    }




    //METODY DLA GRACZA, KTÓRY JEST KLIENTEM

    //tura drugiego gracza, który jest klientem
    public void client_turn(BufferedReader in, GameController gameController) throws IOException {
        String fromClient;
        //out.println("\n\n\nTWOJA TURA ");

        //1. przejścia stworów w stronę bazy przeciwnika
        board.move(opponent, you, discarded, cards,  rage_cards, money, out, in, gameController);

        //wypisanie samej planszy
        System.out.println(board);

        //wysłanie gry, panszy i kasy do przeciwnika
        //out.println(this);
        //out.println(board);
        //out.println("Pieniadze: " + opponent.money);

        //2. dobrania kart stworów lub żetonów waluty. Gracz ma dwa dobrania
        draw2(in, gameController);

        //3.Jeżeli gracz ma coś w ekwipunku...
        if(opponent.eq.size() >= 1) {
            int number = 2;
            //3. ... i ma kartę Rage "Black Market" może...
            if (opponent.BlackMarket == 1) {
                //out.println("Mozesz sprzedac JEDNEGO stwora i zakonczyc ture lub wystawiac karty:");
                //out.println("(1) sprzedaj");
                //out.println("(2) wystaw");
                //out.println("ONE_OR_TWO");
                fromClient = in.readLine();
                if (fromClient.equals("1"))
                    number = 1;
                //... sprzedawać lub...
                if (number == 1)
                    sell2(in);
            }
            //... może wystawiać tyle stworów na ile stać gracza, o ile jakieś ma, przestrzegając limitu 4 swoich stworów na planszy
            if (number == 2)
                display2(in, gameController);
        }

        //4. Jeśli gracz posiada kartę Rage "Secret Assets" dostaje 1 żeton waluty na koniec tury
        if(opponent.SecretAssets == 1){
            opponent.money += money.giveMoney(you, opponent);
            //out.println("Dobrano zeton waluty");
        }

        //4. Jeśli gracz posiada kartę Rage "RatCatcher" dostaje 1 kartę stwora na koniec tury
        if(opponent.RatCatcher == 1){
            opponent.eq.addCreature(cards.giveCard(out, you, opponent, gameController));
            //out.println("Dobrano stwora");
        }

        //out.println("\n\n\nTURA PRZECIWNIKA");
    }

    //pozwala drugiemu graczowi dobrać 2x żeton waluty  lub  2x kartę stwora  lub  1x to i 1x to
    private void draw2(BufferedReader in, GameController gameController) throws IOException {
        opponent.select = 2;   //liczba dobrań
        String fromClient;

        while(opponent.select > 0) {
            //out.println("1 - dobierz karte (lub) 2 - dobierz zeton waluty");
            //out.println("ONE_OR_TWO");
            int number;
            fromClient = in.readLine();
            if (fromClient.equals("1"))
                number = 1;
            else
                number = 2;

            //dobranie karty
            if (number == 1) {
                Creature one = cards.giveCard(out, you, opponent, gameController);
                //jeżeli gracz posiada kartę Rage "Selection" dobiera dwie karty. Jedną zatrzymuje, drugą odrzuca
                if (opponent.Selection == 1) {
                    Creature two = cards.giveCard(out, you, opponent, gameController);
                    //out.println("(1) " + one);
                    //out.println("(2) " + two);
                    //out.println("ONE_OR_TWO");
                    fromClient = in.readLine();
                    if (fromClient.equals("2"))
                        number = 2;
                    if (number == 1)
                        opponent.eq.addCreature(one);
                    else
                        opponent.eq.addCreature(two);
                } else {
                    //out.println(one);
                    opponent.eq.addCreature(one);
                }
                opponent.select--;
            }

            //dobranie żetonu waluty
            //!WARNING SIĘ MYLI - TESTOWANE - Kacper Brzoza
            else if (number == 2) {
                opponent.select--;
                int coin_one = money.giveMoney(you, opponent);
                //jeżeli gracz posiada kartę Rage "Second Chance" dobiera dwa żetony waluty. Jedną zatrzymuje, drugą odrzuca
                if (opponent.SecondChance == 1) {
                    int coin_two = money.giveMoney(you, opponent);
                    //out.println("(1) " + coin_one);
                    //out.println("(2) " + coin_two);
                    //out.println("ONE_OR_TWO");
                    fromClient = in.readLine();
                    if (fromClient.equals("1"))
                        number = 1;
                    if (number == 1)
                        opponent.money += coin_one;
                    else
                        opponent.money += coin_two;
                } else {
                    //out.println("zdobyto: " + coin_one);
                    opponent.money += coin_one;
                }
            }
            //out.println("Pozostale ruchy: " + opponent.select);
        }
    }

    //metoda odpowiadająca za wystawianie kart i dodatkowe akcje
    private void display2(BufferedReader in, GameController gameController) throws IOException {
        String fromClient;
        if(opponent.counter < 4) {
            label:
            while (opponent.counter < 4) {
                //out.println("Pieniadze: " + opponent.money);
                //out.println("1 - wybierz karte \n 2 - podejrzyj plansze \n 3 - spasuj \n 4 - obejrzyj swoje karty Rage \n 5 - legenda mocy stworow");
                //out.println("CHOICE");
                fromClient = in.readLine();
                //wystawienie karty
                switch (fromClient) {
                    case "1":

                        //out.println("\nTwoj ekwpiunek:");
                        //out.println(opponent.eq);
                        //out.println("(" + opponent.eq.size() + ") cofnij");
                        //out.println("Pieniadze: " + opponent.money);

                        do {
                            int number;
                            //println(opponent.eq.size());
                            fromClient = in.readLine();
                            number = Integer.parseInt(fromClient);
                            if (number == 10000) {
                                break;
                            } else {
                                if (opponent.eq.checkCost(number) <= opponent.money) {
                                    opponent.money -= opponent.eq.checkCost(number);
                                    Creature creature = opponent.eq.pickCreature(number);
                                    //zwiększenie ataku stwora w przypadku posiadania karty Rage "Swarm"
                                    if (opponent.Swarm == 1) {
                                        if (creature.getAttack() == 2) {
                                            creature.increaseAttack();
                                            creature.setSwarm(1);
                                        }
                                    }
                                    //zwiększenie hp stwora w przypadku posiadania karty Rage "Unbroaken"
                                    if (opponent.Unbroaken == 1) {
                                        if (creature.getHp() == 2) {
                                            creature.increaseHp();
                                            creature.setUnbroaken(1);
                                        }
                                    }
                                    board.put(creature, opponent, you, discarded);
                                    opponent.counter++;
                                    creature.effect(opponent, you, cards, discarded, money, board, out, in, gameController);
                                    //out.println("\n" + board);
                                    System.out.println("\n" + board);
                                    break;
                                } else {
                                    //out.println("Ta jednostka jest za droga!");
                                }
                            }
                        } while (true);
                        break;
                    //podejrzenie planszy
                    case "2":
                        //out.println(this);
                        //out.println(board);
                        break;
                    //pass
                    case "3":
                        break label;

                    //podejrzenie swoich kart Rage
                    case "4":
                        //out.println(opponent.rage);
                        break;
                    //legenda mocy
                    case "5":
                        /*
                        out.println();
                        out.println("D - przy wystawieniu dobierasz karte stwora");
                        out.println("E - przy wystawieniu pozwala natychmiast wystawic kolejnego stwora z moca E za darmo");
                        out.println("F - jesli na przeciwnym polu nie ma wroga, probuje walczyc z przeciwnikiem o 1 pole dalszym");
                        out.println("G - odsyla pierwszego napotkanego przeciwnika spowrotem do ekwipunku oponenta");
                        out.println("H - przy wystawieniu uzdrawia jednostke, tzn dobierasz karte stwora ze stosu kart odrzuconych");
                        out.println("J - gdy ta jednostka ginie, zmienia hp swojego zabojcy na 1");
                        out.println("M - przy wystawieniu dobierasz zeton waluty");
                        out.println("N - gdy ta jednostka ma zostac zabita, jednorazowo unika smierci");
                        out.println("O - pozwala natychmiast wystawic kolejnego stwora za darmo");
                        out.println("R - gdy sojusznik ma zginac, ta jednostka zginie zamiast tamtej");
                        out.println("U - sprawia, ze jednostki na dwoch polach za nia sa niezniszczalne, dopoki sama zyje");
                        out.println("X - zabija pierwszego napotkanego przeciwnika bez wzgledu na statystyki");
                        out.println("Z - przy wystawieniu pozwala zamienic miejscami ta jednostke z inna sojusznicza");
                        out.println();
                         */
                        break;
                }
            }
        }
        else
        {
            System.out.println("Dotarlem do konca");
        }
            //out.println("Osiagnieto limit 4 kart na planszy!");
    }

    //służy do sprzedawania stworów jeżeli gracz drugi posiada kartę "Black Market"
    private void sell2(BufferedReader in) throws IOException{
        String fromClient;

        //out.println("Wybierz jednostke do sprzedania:");
        //out.println(opponent.eq);
        int number = -1;
        while (number < 0 || number >= opponent.eq.size()) {
            //out.println(opponent.eq.size());
            fromClient = in.readLine();
            number = Integer.parseInt(fromClient);
            if (number < opponent.eq.size()) {
                Creature creature = opponent.eq.pickCreature(number);
                opponent.money += creature.getCost();
                discarded.putCard(creature);
                break;
            }
        }
        //out.println("Sprzedano stwora");
    }


    @Override
    public String toString(){
        String stan_gry = "Gracz 1 | tarcze: " + you.showShields() + " | punkty: " + you.score + "\n";
        stan_gry += "Gracz 2 | tarcze: " + opponent.showShields() + " | punkty: " + opponent.score + "\n";
        return stan_gry;
    }

}
