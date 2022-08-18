package com.example.Meat.Rage_Cards;

import com.example.Meat.Demo.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Weakness extends R_Card{

    public Weakness(){
        name = "Weakness";
        description = "przeciwnik traci 3 karty stworow z reki (jednorazowe)";
    }

    public void effect(Player you, Player opponent, Board board, Discardeds_Stack discardeds, Cards_Stack cards, Money money, Rage_Cards rage, PrintWriter out, BufferedReader in) throws IOException {
        if(opponent.id == 1){
            //zadziała gdy przeciwnik ma coś w ekwipunku
            if(opponent.eq.size() > 0) {
                int counter;
                //ustawienie licznika odrzucania w zleżności od rozmiaru eq przeciwnika
                if (opponent.eq.size() > 3)
                    counter = 3;
                else
                    counter = opponent.eq.size();

                System.out.println("Musisz odrzucic " + counter + " karty z reki");
                Scanner scan = new Scanner(System.in);
                int number;

                //odrzucanie
                while (counter > 0) {
                    System.out.println(opponent.eq);
                    System.out.println("wybierz: ");
                    number = scan.nextInt();
                    if (number >= 0 && number < opponent.eq.size()) {
                        discardeds.putCard(opponent.eq.pickCreature(number));
                        System.out.println("Odrzucono");
                        counter--;
                    }
                }
            }
        }
        else{
            String fromClient;
            //zadziała gdy przeciwnik ma coś w ekwipunku
            if(opponent.eq.size() > 0) {
                int counter;
                //ustawienie licznika odrzucania w zleżności od rozmiaru eq przeciwnika
                if (opponent.eq.size() > 3)
                    counter = 3;
                else
                    counter = opponent.eq.size();

                out.println("Musisz odrzucic " + counter + " karty z reki");
                int number;

                //odrzucanie
                while (counter > 0) {
                    out.println(opponent.eq);
                    out.println("wybierz: ");
                    out.println(opponent.eq.size());
                    fromClient = in.readLine();
                    number = Integer.parseInt(fromClient);
                    if (number >= 0 && number < opponent.eq.size()) {
                        discardeds.putCard(opponent.eq.pickCreature(number));
                        out.println("Odrzucono");
                        counter--;
                    }
                }
            }
        }
    }
}
