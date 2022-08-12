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

        System.out.println("GRACZ " + you.id + " MOZE ZABIC PRZECIWNIKOWI 3 JEDNOSTKI!!!");
        System.out.println(board);
        for(int i = 0; i < 5; i++){
            if(!board.empty(opponent.id, i))
                System.out.print("  (" + i + ")  ");
        }
        System.out.println();

        Scanner scan = new Scanner(System.in);
        int number;
        //zabijanie 3 jednostek lub jeśli przeciwnik ma mniej niż 3 - wszystkich
        while(dead_counter < 3 && opponent.counter > 0){
            System.out.print("wybierz: ");
            number = scan.nextInt();
            if(!board.empty(opponent.id, number) && number >= 0 && number < 5){
                discardeds.putCard(board.removeCard(opponent.id, number));
                dead_counter++;
                opponent.counter--;
            }
        }
        System.out.println("wybierz dowolna liczbe, aby kontynuowac... ");
        number = scan.nextInt();
    }
}
