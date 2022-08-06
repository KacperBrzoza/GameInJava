package Rage_Cards;

import Demo.*;

import java.util.Scanner;

public class Extermination extends R_Card{

    public Extermination(){
        name = "Extermination";
        description = "natychmiast zabijasz rywalowi 3 jednostki (jednorazowe)";
    }

    public void effect(Player you, Player opponent, Board board, Discardeds_Stack discardeds, Cards_Stack cards, Money money, Rage_Cards rage){
        int dead_counter = 0;
        System.out.println("GRACZ " + you.id + " MOŻE ZABIĆ PRZECIWNIKOWI 3 JEDNOSTKI!!!");
        System.out.println(board);
        for(int i = 0; i < 5; i++){
            if(!board.empty(opponent.id, i))
                System.out.print("  (" + i + ")  ");
        }
        Scanner scan = new Scanner(System.in);
        int number;
        while(dead_counter < 3 && opponent.counter > 0){
            System.out.print("\nwybierz: ");
            number = scan.nextInt();
            if(!board.empty(opponent.id, number)){
                discardeds.putCard(board.removeCard(opponent.id, number));
                dead_counter++;
                opponent.counter--;
            }
        }
        System.out.println("wybierz dowolną liczbę, aby kontynuować... ");
        number = scan.nextInt();
    }
}
