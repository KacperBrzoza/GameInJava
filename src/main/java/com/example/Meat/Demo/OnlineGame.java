package com.example.Meat.Demo;

import com.example.Main.Game.GameController;
import com.example.Meat.Creatures.*;
import javafx.application.Platform;
import javafx.scene.image.Image;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.*;
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
    }


    //początkowe zasoby dla każdego gracza (3 żetony waluty i 3 karty stworów)
    private void startGame(Player you, Player opponent, GameController gameController){
        for(int i = 0; i < 5; i++){
            Creature creature = cards.giveCard(out, you, opponent, gameController);
            opponent.eq.addCreature(creature);
            GameController.server.sendMessageToClient("PATH_" + creature.path);

            creature = cards.giveCard(out, you, opponent, gameController);
            you.eq.addCreature(creature);                                                                                       //dodanie stwora do eq gracza 1
            GameController.addImageToEQ(gameController.eqImages, creature.path);                                                 //dodanie zdjecia stwora do listy od eq


            you.money += money.giveMoney(you, opponent);
            opponent.money += money.giveMoney(you, opponent);

        }
        //ponizsze linijki to wyswietlanie informacji u graczy
        GameController.server.sendMessageToClient("NEW_CARDS_STACK_SIZE_" + cards.size());
        GameController.newLabelValue(gameController.CardCounter, "" + cards.size());
        GameController.server.sendMessageToClient("NEW_MY_MONEY_VAL_" + opponent.money);
        GameController.newLabelValue(gameController.MoneyPlayerValue, "" + you.money);
        GameController.server.sendMessageToClient("SHOW_EQ");
        GameController.showEQ(gameController.eq_it, gameController.eqImages, gameController.EQ1, gameController.EQ2, gameController.EQ3, gameController.EQ4);
    }


    //METODY DLA GRACZA, KTÓRY JEST SERWEREM

    //tura pierwszego gracza
    public void server_turn(BufferedReader in, GameController gameController) throws IOException {
        GameController.choice = -1;
        GameController.changeTurn(gameController.EndTurnButton, gameController.TakeCardDeck, gameController.RageCardDeck, gameController.MoneyStack, gameController.LostCardDeck, gameController.CardCounter);
        GameController.server.sendMessageToClient("PHASE_0");

        //1. przejścia stworów w stronę bazy przeciwnika
        GameController.phase = 1;
        board.move(you, opponent, discarded, cards,  rage_cards, money, out, in, gameController);
        GameController.showBattleField(gameController.fields, gameController.mygrid0, gameController.mygrid1, gameController.mygrid2, gameController.mygrid3, gameController.mygrid4, gameController.enemygrid0, gameController.enemygrid1, gameController.enemygrid2, gameController.enemygrid3, gameController.enemygrid4);
        

        //2. dobrania kart stworów lub żetonów waluty. Gracz ma dwa dobrania
        GameController.phase = 2;
        draw(gameController);


        //3.Jeżeli gracz ma coś w ekwipunku...
        GameController.phase = 3;
        if(you.eq.size() >= 1) {
            int number = 2;
            /*
            //3. ... i ma kartę Rage "Black Market" może...
            if (you.BlackMarket == 1) {
                number = -1;
                System.err.println("Mozesz sprzedac JEDNEGO stwora i zakonczyc ture lub wystawiac karty:");
                System.err.println("(1) sprzedaj");
                System.err.println("(2) wystaw");
                while (number < 1 || number > 2) {
                    System.err.print("wybierz: ");
                    number = scan.nextInt();
                }
                //... sprzedawać lub...
                if (number == 1)
                    sell();
            }*/
            //... może wystawiać tyle stworów na ile stać gracza, o ile jakieś ma, przestrzegając limitu 4 swoich stworów na planszy
            if (number == 2) {
                display(in, gameController);
            }
        }


        //4. Jeśli gracz posiada kartę Rage "Secret Assets" dostaje 1 żeton waluty na koniec tury
        GameController.phase = 4;
        if(you.SecretAssets == 1){
            you.money += money.giveMoney(you, opponent);
            GameController.newLabelValue(gameController.MoneyPlayerValue, "" + you.money);
        }

        //4. Jeśli gracz posiada kartę Rage "RatCatcher" dostaje 1 kartę stwora na koniec tury
        if(you.RatCatcher == 1){
            Creature creature = cards.giveCard(out, you, opponent, gameController);
            you.eq.addCreature(creature);
            GameController.addImageToEQ(gameController.eqImages, creature.path);
            GameController.showEQ(gameController.eq_it, gameController.eqImages, gameController.EQ1, gameController.EQ2, gameController.EQ3, gameController.EQ4);
        }
    }

    //pozwala pierwszemu graczowi dobrać 2x żeton waluty  lub  2x kartę stwora  lub  1x to i 1x to
    private void draw(GameController gameController){
        you.select = 2;   //liczba dobrań

        GameController.selectingPhase(gameController.TakeCardDeckSelect, gameController.MoneyStackSelect, gameController.EndTurnButton);

        while(you.select > 0){
            int number = -1;
            while (number == -1){
                number = GameController.choice;
                System.out.println("\n");
                if(number == 1 || number == 2)
                    break;
            }

            //dobranie karty
            if(number == 1){
                you.select--;
                Creature one = cards.giveCard(out, you, opponent, gameController);
                /*
                //jeżeli gracz posiada kartę Rage "Selection" dobiera dwie karty. Jedną zatrzymuje, drugą odrzuca
                if(you.Selection == 1){
                    Creature two = cards.giveCard(out, you, opponent, gameController);
                    File file1 = new File(one.path);
                    Image first = new Image(file1.toURI().toString());
                    File file2 = new File(two.path);
                    Image second = new Image(file2.toURI().toString());
                    GameController.setChoiceHBox(gameController.ChoiceHBox, gameController.InventoryPane, gameController.EndTurnButton, gameController.choiceOne, gameController.choiceTwo, first, second);
                    number = -1;
                    while (number == -1){
                        number = GameController.choice;
                    }

                    if(number == 1) {
                        System.err.println("wybrano " + one);
                        you.eq.addCreature(one);
                        GameController.addImageToEQ(gameController.eqImages, one.path);
                    }
                    else {
                        System.err.println("wybrano " + two);
                        you.eq.addCreature(two);
                        GameController.addImageToEQ(gameController.eqImages, two.path);
                    }
                }
                else{

                 */
                    you.eq.addCreature(one);
                    GameController.addImageToEQ(gameController.eqImages, one.path);
                //}
            }

            //dobranie żetonu waluty
            else if(number == 2){
                you.select--;
                int coin_one = money.giveMoney(you, opponent);
                /*
                //jeżeli gracz posiada kartę Rage "Second Chance" dobiera dwa żetony waluty. Jedną zatrzymuje, drugą odrzuca
                if(you.SecondChance == 1){
                    int coin_two = money.giveMoney(you, opponent);
                    System.err.println("(1) " + coin_one);
                    System.err.println("(2) " + coin_two);
                    number = -1;
                    while (number < 1 || number > 2){
                        System.err.print("wybierz: ");
                        //number = scan.nextInt();
                    }
                    if(number == 1)
                        you.money += coin_one;
                    else
                        you.money += coin_two;
                }
                else {

                 */
                    you.money += coin_one;
                //}
                GameController.newLabelValue(gameController.MoneyPlayerValue, "" + you.money);
            }
            GameController.choice = -1;
        }
        GameController.selectingPhase(gameController.TakeCardDeckSelect, gameController.MoneyStackSelect, gameController.EndTurnButton);
    }



    //metoda odpowiadająca za wystawianie kart i dodatkowe akcje
    private void display(BufferedReader in, GameController gameController) throws IOException {
        while (you.counter < 4 ) {
            int number = -1;
            while (number == -1){
                number = GameController.choice;
                System.out.println("\n");
                if(number != -1)
                    break;
            }
            if(number == -2){
                break;
            }
            else if(number < you.eq.size()){
                if (you.eq.checkCost(number) <= you.money) {
                    you.money -= you.eq.checkCost(number);
                    GameController.newLabelValue(gameController.MoneyPlayerValue, "" + you.money);
                    GameController.removeImageFromEQ(gameController.eqImages, number, gameController.eq_it, gameController.RightShowBut);
                    GameController.showEQ(gameController.eq_it, gameController.eqImages, gameController.EQ1, gameController.EQ2, gameController.EQ3, gameController.EQ4);
                    Creature creature = you.eq.pickCreature(number);
                /*
                      //zwiększenie ataku stwora w przypadku posiadania karty Rage "Swarm"
                       if (you.Swarm == 1) {
                            if (creature.getAttack() == 2) {
                                creature.increaseAttack();
                                creature.setSwarm(1);
                            }
                        }
                        //zwiększenie hp stwora w przypadku posiadania karty Rage "Unbroaken"
                        if (you.Unbroaken == 1) {
                            if (creature.getHp() == 2) {
                                creature.increaseHp();
                                creature.setUnbroaken(1);
                            }
                        }*/
                    board.put(creature, you, opponent, discarded, gameController);
                    you.counter++;
                    creature.effect(you, opponent, cards, discarded, money, board, out, in, gameController);
                    //out.println("\n" + board);
                } else {
                    GameController.newLabelValue(gameController.InfoLabel, "Ta jednostka jest za droga!");
                    gameController.disable_button_sound();
                }
            }
            GameController.choice = -1;
        }
    }

    //służy do sprzedawania stworów jeżeli gracz pierwszy posiada kartę "Black Market"
    private void sell(){
        System.err.println("Wybierz jednostke do sprzedania:");
        System.err.println(you.eq);
        Scanner scan = new Scanner(System.in);
        int number = -1;
        while (number < 0 || number >= you.eq.size()){
            System.err.print("Wybierz: ");
            number = scan.nextInt();
            if(number >= 0 && number < you.eq.size()){
                Creature creature = you.eq.pickCreature(number);
                you.money += creature.getCost();
                discarded.putCard(creature);
                break;
            }
        }
        System.err.println("Sprzedano stwora");
    }




    //METODY DLA GRACZA, KTÓRY JEST KLIENTEM

    //tura drugiego gracza, który jest klientem
    public void client_turn(BufferedReader in, GameController gameController) throws IOException {
        GameController.phase = 0;

        //1. przejścia stworów w stronę bazy przeciwnika
        GameController.server.sendMessageToClient("PHASE_1");
        board.move(opponent, you, discarded, cards,  rage_cards, money, out, in, gameController);
        GameController.showBattleField(gameController.fields, gameController.mygrid0, gameController.mygrid1, gameController.mygrid2, gameController.mygrid3, gameController.mygrid4, gameController.enemygrid0, gameController.enemygrid1, gameController.enemygrid2, gameController.enemygrid3, gameController.enemygrid4);

        //2. dobrania kart stworów lub żetonów waluty. Gracz ma dwa dobrania
        GameController.server.sendMessageToClient("PHASE_2");
        try {
            draw2(in, gameController);
        }catch (IOException e){
            System.err.println("LEVEL 1");
            throw e;
        }

        //3.Jeżeli gracz ma coś w ekwipunku...
        GameController.server.sendMessageToClient("PHASE_3");
        if(opponent.eq.size() >= 1) {
            int number = 2;
            /*
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

             */
            if (number == 2) {
                try {
                    display2(in, gameController);
                } catch (IOException e){
                    System.err.println("LEVEL 1");
                    throw e;
                }
            }
        }


        //4.Jeśli gracz posiada kartę Rage "Secret Assets" dostaje 1 żeton waluty na koniec tury
        if(opponent.SecretAssets == 1){
            opponent.money += money.giveMoney(you, opponent);
            GameController.server.sendMessageToClient("NEW_MY_MONEY_VAL_" + opponent.money);
        }

        //4. Jeśli gracz posiada kartę Rage "RatCatcher" dostaje 1 kartę stwora na koniec tury
        if(opponent.RatCatcher == 1){
            Creature creature = cards.giveCard(out, you, opponent, gameController);
            opponent.eq.addCreature(creature);
            GameController.server.sendMessageToClient("PATH_" + creature.path);
            GameController.server.sendMessageToClient("SHOW_EQ");
        }
    }

    //pozwala drugiemu graczowi dobrać 2x żeton waluty  lub  2x kartę stwora  lub  1x to i 1x to
    private void draw2(BufferedReader in, GameController gameController) throws IOException {
        opponent.select = 2;   //liczba dobrań
        String clientMessage;
        GameController.server.sendMessageToClient("SELECTING_PHASE");

        while(opponent.select > 0) {
            try {
                clientMessage = GameController.server.waitForClientChoice();
            }catch (IOException e){
                System.err.println("LEVEL 0");
                throw e;
            }

            int number = Integer.parseInt(clientMessage);
            //dobranie karty
            if (number == 1) {
                opponent.select--;
                Creature one = cards.giveCard(out, you, opponent, gameController);
                /*
                //jeżeli gracz posiada kartę Rage "Selection" dobiera dwie karty. Jedną zatrzymuje, drugą odrzuca
                if (opponent.Selection == 1) {
                    Creature two = cards.giveCard(out, you, opponent, gameController);
                    //out.println("(1) " + one);
                    //out.println("(2) " + two);
                    //out.println("ONE_OR_TWO");
                    //fromClient = in.readLine();
                    //if (fromClient.equals("2"))
                        number = 2;
                    if (number == 1)
                        opponent.eq.addCreature(one);
                    else
                        opponent.eq.addCreature(two);
                } else {*/
                    opponent.eq.addCreature(one);
                GameController.server.sendMessageToClient("PATH_" + one.path);
                //}
            }

            else if (number == 2) {
                opponent.select--;
                int coin_one = money.giveMoney(you, opponent);
                /*
                //jeżeli gracz posiada kartę Rage "Second Chance" dobiera dwa żetony waluty. Jedną zatrzymuje, drugą odrzuca
                if (opponent.SecondChance == 1) {
                    int coin_two = money.giveMoney(you, opponent);
                    //out.println("(1) " + coin_one);
                    //out.println("(2) " + coin_two);
                    //out.println("ONE_OR_TWO");
                    //fromClient = in.readLine();
                    //if (fromClient.equals("1"))
                        number = 1;
                    if (number == 1)
                        opponent.money += coin_one;
                    else
                        opponent.money += coin_two;
                } else {*/
                    opponent.money += coin_one;
                //}
                GameController.server.sendMessageToClient("NEW_MY_MONEY_VAL_" + opponent.money);
            }
            else if (number == 9999) {
                throw new IOException();
            }
        }
        GameController.server.sendMessageToClient("SELECTING_PHASE");
    }

    //metoda odpowiadająca za wystawianie kart i dodatkowe akcje
    private void display2(BufferedReader in, GameController gameController) throws IOException {
        String clientMessage;
        int number = -1;

        while (opponent.counter < 4) {

            try {
                clientMessage = GameController.server.waitForClientChoice();
            }catch (IOException e){
                System.err.println("LEVEL 0");
                throw e;
            }
            number = Integer.parseInt(clientMessage);
            if(number == 10000){
                break;
            }else if (number == 9999) {
                throw new IOException();
            }
            else if(number < opponent.eq.size()){
                if (opponent.eq.checkCost(number) <= opponent.money) {
                    opponent.money -= opponent.eq.checkCost(number);
                    GameController.server.sendMessageToClient("NEW_MY_MONEY_VAL_" + opponent.money);
                    GameController.server.sendMessageToClient("REMOVE_FROM_EQ");
                    GameController.server.sendMessageToClient("SHOW_EQ");
                    Creature creature = opponent.eq.pickCreature(number);
                /*
                      //zwiększenie ataku stwora w przypadku posiadania karty Rage "Swarm"
                       if (you.Swarm == 1) {
                            if (creature.getAttack() == 2) {
                                creature.increaseAttack();
                                creature.setSwarm(1);
                            }
                        }
                        //zwiększenie hp stwora w przypadku posiadania karty Rage "Unbroaken"
                        if (you.Unbroaken == 1) {
                            if (creature.getHp() == 2) {
                                creature.increaseHp();
                                creature.setUnbroaken(1);
                            }
                        }*/
                    board.put(creature, opponent, you, discarded, gameController);
                    opponent.counter++;
                    creature.effect(opponent, you, cards, discarded, money, board, out, in, gameController);
                } else {
                    GameController.server.sendMessageToClient("EXPENSIVE");
                }
            }
        }
        GameController.server.sendMessageToClient("PHASE_4");
        if(!(number == 10000))
            number = -1;
        while (number == -1){
            try {
                clientMessage = GameController.server.waitForClientChoice();
            } catch (IOException e){
                System.err.println("LEVEL 0");
                throw e;
            }

            number = Integer.parseInt(clientMessage);
            if (number == 9999) {
                throw new IOException();
            }
            else if (number == 10000) {
                break;
            }
        }
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



}
