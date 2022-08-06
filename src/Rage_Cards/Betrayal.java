package Rage_Cards;

import Demo.*;

import java.util.Scanner;

public class Betrayal extends R_Card{

    public Betrayal(){
        name = "Betrayal";
        description = "wybrana jednostka rywala przechodzi na twoją stronę (jednorazowe)";
    }

    public void effect(Player p2, Player p1, Board board, Discardeds_Stack discardeds, Cards_Stack cards, Money money){
        //jeżeli przeciwnik ma wystawione jakieś stwory
        if(p1.counter > 0){
            System.out.println("\n" + "\n" + "\n" + "\n" + "\n"  + "\n" + "\n" + "\n" + "\n" + "\n");
            System.out.println("GRACZ " + p2.id + " MOŻE PRZECIĄGNĄĆ STWORA NA SWOJĄ STRONĘ!!!");
            System.out.println(board);
            for(int i = 0; i < 5; i++){
                //wybierz te pola, które przeciwnik ma zajęte, a ty masz wolne
                if(!board.empty(p1.id, i) && (board.empty(p2.id, i))){
                    System.out.print("  (" + i + ")  ");
                }
            }
            System.out.println("\n(5) aby zrezygnować");
            Scanner scan = new Scanner(System.in);
            int number = -1;
            while (number < 0 || number > 5){
                System.out.print("wybierz: ");
                number = scan.nextInt();
                if(number >=0 && number < 5) {
                    //jeżeli wybrane pole przeciwnika nie jest puste, a aktualnego gracza wolne...
                    if (!board.empty(p1.id, number) && board.empty(p2.id, number)) {
                        //...wtedy stwór przeciwnika przechodzi na stronę aktualnego gracza
                        board.insertCard(board.removeCard(p1.id, number), number, p2.id);
                    }
                    else{
                        number = -1;
                    }
                }
                else if(number == 5){
                    System.out.print("zrezygnowano ");
                }
            }
            System.out.print("wybierz dowolną liczbę, aby kontynuować... ");
            number = scan.nextInt();
        }
    }
}
