package Demo;

import Creatures.Creature;

import java.util.Scanner;

public class Game {
    Player you;                  //gracz 1
    Player opponent;                  //gracz 2
    Cards_Stack cards;          //zakryty "stos" kart stworów
    Discardeds_Stack discarded; //odkryty stos kart odrzuconych stworów
    Money money;                //zakryty "stos" żetonów waluty
    Board board;                //plansza
    Rage_Cards rage_cards;      //zakryty "stos" kart Rage



    public Game(){
        you = new Player(1);
        opponent = new Player(2);
        cards = new Cards_Stack();
        discarded = new Discardeds_Stack();
        money = new Money();
        board = new Board();

        rage_cards = new Rage_Cards();
        rage_cards.initialization();

        startGame(you);
        startGame(opponent);
        while(true){
            turn(you, opponent);
            turn(opponent, you);
        }
    }

    //początkowe zasoby dla każdego gracza (3 żetony waluty i 3 karty stworów)
    public void startGame(Player p){
        for(int i = 0; i < 3; i++){
            p.eq.addCreature(cards.giveCard());
            p.money += money.giveMoney();
        }
    }

    //tura składa się z:
    public void turn(Player you, Player opponent){

        System.out.println("\nTURA GRACZA " + you.id);

        //1. przejścia stworów w stronę bazy przeciwnika
        board.move(you, opponent, discarded, cards,  rage_cards, money);

        System.out.println(this);
        System.out.println(board);
        you.showMoney();

        //2. dobrania kart stworów lub żetonów waluty. Gracz ma dwa dobrania
        draw(you);
        clear();

        //3. wystawienia tylu stworów na ile stać gracza, o ile jakieś ma, przestrzegając limitu 4 swoich stworów na planszy
        while (you.counter < 4 && you.eq.size() >= 1){
            System.out.println("Pieniądze: " + you.money);
            System.out.println("1 - wybierz karte \n 2 - podejrzyj plansze \n 3 - spasuj \n 4 - obejrzyj swoje karty Rage");

            Scanner scan = new Scanner(System.in);
            int number = scan.nextInt();
            //wystawienie karty
            if(number == 1){
                System.out.println("\nTwój ekwpiunek:");
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
                            board.put(you.eq.pickCreature(number), you, opponent, discarded);
                            you.counter++;
                            System.out.println("\n" + board);
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
        }

        //wyczyszczenie ekranu na koniec tury
        clear();
    }

    //pozwala graczowi dobrać 2x żeton waluty  lub  2x kartę stwora  lub  1x to i 1x to
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
        String stan_gry = "Gracz 1 | tarcze: " + you.showShields() + " | punkty: " + you.score + "\n";
        stan_gry += "Gracz 2 | tarcze: " + opponent.showShields() + " | punkty: " + opponent.score + "\n";
        return stan_gry;
    }

    public void clear(){
        System.out.println("\n" + "\n" + "\n" + "\n" + "\n"  + "\n" + "\n" + "\n" + "\n" + "\n");
    }
}
