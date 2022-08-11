package Rage_Cards;

import Demo.*;

import java.util.Scanner;

public class Weakness extends R_Card{

    public Weakness(){
        name = "Weakness";
        description = "przeciwnik traci 3 karty stworów z ręki (jednorazowe)";
    }

    public void effect(Player you, Player opponent, Board board, Discardeds_Stack discardeds, Cards_Stack cards, Money money, Rage_Cards rage){
        if(opponent.eq.size() > 0){
            int counter;
            if(opponent.eq.size() > 3)
                counter = 3;
            else
                counter = opponent.eq.size();
            System.out.println("Musisz odrzucić " + counter + " karty z ręki");
            System.out.println(opponent.eq);
            Scanner scan = new Scanner(System.in);
            int number;
            while (counter > 0){
                System.out.println("wybierz: ");
                number = scan.nextInt();
                if(number >=0 && number < opponent.eq.size()) {
                    discardeds.putCard(opponent.eq.pickCreature(number));
                    System.out.println("Odrzucono");
                    counter--;
                    System.out.println(opponent.eq);
                }
            }
            System.out.println("wybierz dowolną liczbę, aby GRACZ " + opponent.id + " kontynuował turę... ");
            number = scan.nextInt();
        }
    }
}
