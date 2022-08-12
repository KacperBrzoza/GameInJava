package Rage_Cards;

import Demo.*;

import java.util.Scanner;

public class Weakness extends R_Card{

    public Weakness(){
        name = "Weakness";
        description = "przeciwnik traci 3 karty stworow z reki (jednorazowe)";
    }

    public void effect(Player you, Player opponent, Board board, Discardeds_Stack discardeds, Cards_Stack cards, Money money, Rage_Cards rage){
        //zadziała gdy przeciwnik ma coś w ekwipunku
        if(opponent.eq.size() > 0){
            int counter;
            //ustawienie licznika odrzucania w zleżności od rozmiaru eq przeciwnika
            if(opponent.eq.size() > 3)
                counter = 3;
            else
                counter = opponent.eq.size();

            System.out.println("Musisz odrzucic " + counter + " karty z reki");
            Scanner scan = new Scanner(System.in);
            int number;

            //odrzucanie
            while (counter > 0){
                System.out.println(opponent.eq);
                System.out.println("wybierz: ");
                number = scan.nextInt();
                if(number >=0 && number < opponent.eq.size()) {
                    discardeds.putCard(opponent.eq.pickCreature(number));
                    System.out.println("Odrzucono");
                    counter--;
                }
            }
        }
    }
}
