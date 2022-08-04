package Demo;

import Creatures.Creature;

import java.io.IOException;
import java.util.Scanner;

public class Game {
    Player p1;                  //gracz 1
    Player p2;                  //gracz 2
    Cards_Stack cards;          //zakryty "stos" kart stworów
    Discardeds_Stack discarded; //odkryty stos kart odrzuconych stworów
    Money money;                //zakryty "stos" żetonów waluty
    Board board;                //plansza
    Rage_Cards rage_cards;      //zakryty "stos" kart Rage



    public Game(){
        p1 = new Player(1);
        p2 = new Player(2);
        cards = new Cards_Stack();
        discarded = new Discardeds_Stack();
        money = new Money();
        board = new Board();

        rage_cards = new Rage_Cards();
        rage_cards.initialization();

        startGame(p1);
        startGame(p2);
        while(true){
            turn(p1, p2);
            turn(p2, p1);
        }
    }

    //początkowe zasoby dla każdego gracza
    public void startGame(Player p){
        for(int i = 0; i < 5; i++){
            p.eq.addCreature(cards.giveCard());
            p.money += money.giveMoney();
        }
    }

    //tura składa się z:
    public void turn(Player p1, Player p2){

        System.out.println("\nTURA GRACZA " + p1.id);

        //1. przejścia stworów w stronę bazy przeciwnika
        board.move(p1, p2, discarded, rage_cards);
        System.out.println(this);
        System.out.println(board);

        //2. dobrania kart stworów lub żetonów waluty. Gracz ma dwa dobrania
        draw(p1);
        clear();

        //3. wystawienia tylu stworów na ile stać gracza, o ile jakieś ma, przestrzegając limitu 4 swoich stworów na planszy
        while (p1.counter < 4 && p1.eq.size() >= 1){
            System.out.println("Pieniądze: " + p1.money);
            System.out.println("1 - wybierz karte \n 2 - podejrzyj plansze \n 3 - spasuj");

            Scanner scan = new Scanner(System.in);
            int number = scan.nextInt();
            //wystawienie karty
            if(number == 1){
                System.out.println("\nTwój ekwpiunek:");
                System.out.println(p1.eq);
                System.out.println("(" + p1.eq.size() + ") cofnij");
                System.out.println("Pieniądze: " + p1.money);

                number = -1;
                while (number < 0 || number > p1.eq.size()){
                    System.out.print("wybierz: ");
                    number = scan.nextInt();
                    if(number >= 0 && number < p1.eq.size()){
                        if(p1.eq.checkCost(number) <= p1.money) {
                            p1.money -= p1.eq.checkCost(number);
                            board.put(p1.eq.pickCreature(number), p1, p2, discarded);
                            p1.counter++;
                            System.out.println("\n" + board);
                        }
                        else {
                            System.out.println("Ta jednostka jest za droga!");
                            number = -1;
                        }
                    }
                    else if(number == p1.eq.size())
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
        }

        //wyczyszczenie ekranu na koniec tury
        clear();
    }

    //pozwala graczowi dobrać 2x żeton waluty || 2x kartę stwora || 1x to i 1x to
    public void draw(Player p){
        p.select = 2;
        while(p.select > 0){
            Scanner scan =  new Scanner(System.in);
            System.out.println("1 - dobierz kartę (lub) 2 - dobierz żeton waluty");
            System.out.print("wybierz: ");
            String number = scan.nextLine();
            if(number.equals("1")){
                p.select--;
                Creature creature = cards.giveCard();
                System.out.println(creature);
                p.eq.addCreature(creature);
            }
            else if(number.equals("2")){
                p.select--;
                int coin = money.giveMoney();
                System.out.println(coin);
                p.money += coin;
            }
            System.out.println("Pozostałe ruchy: " + p.select);
        }
    }

    @Override
    public String toString(){
        String stan_gry = "Gracz 1 | tarcze: " + p1.showShields() + " | punkty: " + p1.score + "\n";
        stan_gry += "Gracz 2 | tarcze: " + p2.showShields() + " | punkty: " + p2.score + "\n";
        return stan_gry;
    }

    public void clear(){
        System.out.println("\n" + "\n" + "\n" + "\n" + "\n"  + "\n" + "\n" + "\n" + "\n" + "\n");
    }
}
