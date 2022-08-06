package Rage_Cards;

import Demo.*;

import java.util.Scanner;

//pozwala natychmiast wystawić jednostkę
public class FinalWord extends R_Card{

    public FinalWord(){
        this.name = "Final Word";
        this.description = "natychmiast wystawiasz jednostkę za darmo (jednorazowa)";
    }

    public void effect(Player p2, Player p1, Board board, Discardeds_Stack discardeds, Cards_Stack cards, Money money){
        if(p2.eq.size() > 0 && p2.counter < 4){
            System.out.println("\n" + "\n" + "\n" + "\n" + "\n"  + "\n" + "\n" + "\n" + "\n" + "\n");
            System.out.println("GRACZ " + p2.id + " WYSTAWIA TERAZ DARMOWEGO STWORA!!!");
            System.out.println(board);
            System.out.println(p2.eq);
            int number = -1;
            Scanner scan = new Scanner(System.in);
            while (number < 0 || number >= p2.eq.size()){
                System.out.print("wybierz: ");
                number = scan.nextInt();
                if(number >=0 && number < p2.eq.size()){
                    board.put(p1.eq.pickCreature(number), p2, p1, discardeds);
                }
            }
            System.out.println(board);
            System.out.print("wybierz dowolną liczbę, by kontynuować... ");
            scan.nextInt();
            System.out.println("\n");
        }
    }
}
