package Rage_Cards;

import Demo.*;

import java.util.Scanner;

//pozwala natychmiast wystawić jednostkę
public class FinalWord extends R_Card{

    public FinalWord(){
        this.name = "Final Word";
        this.description = "natychmiast wystawiasz jednostkę za darmo (jednorazowa)";
    }

    public void effect(Player you, Player opponent, Board board, Discardeds_Stack discardeds, Cards_Stack cards, Money money, Rage_Cards rage){
        //jeżeli aktualny gracz ma coś w ekwipunku i nie ma 4 jednostek na planszy
        if(you.eq.size() > 0 && you.counter < 4){
            System.out.println("\n" + "\n" + "\n" + "\n" + "\n"  + "\n" + "\n" + "\n" + "\n" + "\n");
            System.out.println("GRACZ " + you.id + " WYSTAWIA TERAZ DARMOWEGO STWORA!!!");
            System.out.println(board);
            System.out.println(you.eq);
            int number = -1;
            Scanner scan = new Scanner(System.in);
            while (number < 0 || number >= you.eq.size()){
                System.out.print("wybierz: ");
                number = scan.nextInt();
                //tu następuje właściwy wybór
                if(number >=0 && number < you.eq.size()){
                    board.put(opponent.eq.pickCreature(number), you, opponent, discardeds);
                    you.counter++;
                }
            }
            System.out.println(board);
            System.out.print("wybierz dowolną liczbę, by kontynuować... ");
            scan.nextInt();
            System.out.println("\n");
        }
    }
}
