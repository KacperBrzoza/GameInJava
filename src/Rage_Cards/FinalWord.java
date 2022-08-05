package Rage_Cards;

import Demo.*;

import java.util.Scanner;

//pozwala natychmiast wystawić jednostkę
public class FinalWord extends R_Card{

    public FinalWord(){
        this.name = "Final Word";
        this.description = "natychmiast wystawiasz jednostkę za darmo (jednorazowa)";
    }

    public void effect(Player p1, Player p2, Board board, Discardeds_Stack discardeds, Cards_Stack cards, Money money){
        if(p1.eq.size() > 0 && p1.counter < 4){
            System.out.println("\n" + "\n" + "\n" + "\n" + "\n"  + "\n" + "\n" + "\n" + "\n" + "\n");
            System.out.println("GRACZ " + p1.id + " WYSTAWIA TERAZ DARMOWEGO STWORA!!!");
            System.out.println(board);
            System.out.println(p1.eq);
            int number = -1;
            Scanner scan = new Scanner(System.in);
            while (number < 0 || number >= p1.eq.size()){
                System.out.print("wybierz: ");
                number = scan.nextInt();
                if(number >=0 && number < p1.eq.size()){
                    board.put(p1.eq.pickCreature(number), p1, p2, discardeds);
                }
            }
            System.out.println(board);
            System.out.print("wybierz dowolną liczbę, by kontynuować... ");
            scan.nextInt();
            System.out.println("\n");
        }
    }
}
