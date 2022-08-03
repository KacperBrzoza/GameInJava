package Demo;

import java.util.Scanner;

public class Game {
    Player p1;
    Player p2;
    Cards_Stack cards;
    Discardeds_Stack discarded;
    Money money;
    Board board;
    Rage_Cards rage_cards;



    public Game(){
        p1 = new Player(1);
        p2 = new Player(2);
        cards = new Cards_Stack();
        discarded = new Discardeds_Stack();
        money = new Money();
        board = new Board();

        rage_cards = new Rage_Cards();
        rage_cards.initialization();

        while(true){
            turn(p1, p2);
            turn(p2, p1);
        }
    }

    public void turn(Player p1, Player p2){

        board.move(p1, p2, discarded, rage_cards);

        draw(p1);

        while (p1.counter < 4 && p1.eq.size() >= 1){
            System.out.println("1 - wybierz karte \n 2 - podejrzyj plansze \n 3 - spasuj");
            Scanner scan = new Scanner(System.in);
            int number = scan.nextInt();
            if(number == 1){
                System.out.println(p1.eq);
                System.out.println("(" + p1.eq.size() + ") cofnij");
                number = -1;
                while (number < 0 || number >= p1.eq.size()){
                    System.out.print("wybierz: ");
                    number = scan.nextInt();
                    if(number >= 0 || number < p1.eq.size()){
                        board.put(p1.eq.pickCreature(number), p1, p2, discarded);
                        p1.counter++;
                    }
                }
            }
            else if(number == 2){
                System.out.println(board);
            }
            else if(number == 3){
                break;
            }
        }
    }

    public void draw(Player p){
        p.select = 2;
        while(p.select > 0){
            Scanner scan =  new Scanner(System.in);
            System.out.println("1 - dobierz karte || 2 - dobierz zeton waluty");
            int number = scan.nextInt();
            if(number == 1){
                p.select--;
                p.eq.addCreature(cards.giveCard());
            }
            else if(number == 2){
                p.select--;
                p.money += money.giveMoney();
            }
            else
                System.out.println("wybierz 1 lub 2");
        }
    }
}
