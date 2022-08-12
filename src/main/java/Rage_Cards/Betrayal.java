package Rage_Cards;

import Demo.*;

import java.util.Scanner;

public class Betrayal extends R_Card{

    public Betrayal(){
        name = "Betrayal";
        description = "wybrana jednostka rywala przechodzi na twoja strone (jednorazowe)";
    }

    public void effect(Player you, Player opponent, Board board, Discardeds_Stack discardeds, Cards_Stack cards, Money money, Rage_Cards rage){
        //jeżeli przeciwnik ma wystawione jakieś stwory, a aktualny gracz nie przekroczył limitu 4 jednostek na planszy
        if(opponent.counter > 0 && you.counter < 4){

            System.out.println("\n" + "\n" + "\n" + "\n" + "\n"  + "\n" + "\n" + "\n" + "\n" + "\n");
            System.out.println("GRACZ " + you.id + " MOZE PRZECIAGNAC STWORA NA SWOJA STRONĘ!!!");
            System.out.println(board);
            for(int i = 0; i < 5; i++){
                //wybierz te pola, które przeciwnik ma zajęte, a ty masz wolne
                if(!board.empty(opponent.id, i) && (board.empty(you.id, i))){
                    System.out.print("  (" + i + ")  ");
                }
            }
            System.out.println("\n(5) aby zrezygnowac");

            Scanner scan = new Scanner(System.in);
            int number = -1;
            while (number < 0 || number > 5){
                System.out.print("wybierz: ");
                number = scan.nextInt();
                if(number >=0 && number < 5) {
                    //jeżeli wybrane pole przeciwnika nie jest puste, a aktualnego gracza wolne...
                    if (!board.empty(opponent.id, number) && board.empty(you.id, number)) {
                        //...wtedy stwór przeciwnika przechodzi na stronę aktualnego gracza
                        board.insertCard(board.removeCard(opponent.id, number), number, you.id);
                        opponent.counter--;
                        you.counter++;
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
