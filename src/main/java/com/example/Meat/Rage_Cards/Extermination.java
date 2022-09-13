package com.example.Meat.Rage_Cards;

import com.example.Main.Game.GameController;
import com.example.Meat.Demo.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Extermination extends R_Card{

    public Extermination(){
        name = "Extermination";
        description = "natychmiast zabijasz rywalowi 3 jednostki (jednorazowe)";
        this.path = "src/main/resources/img/RAGE_powers/extermination.png";
    }

    public void effect(Player you, Player opponent, Board board, Discardeds_Stack discardeds, Cards_Stack cards, Money money, Rage_Cards rage, BufferedWriter out, BufferedReader in, GameController gameController) throws IOException {
        int dead_counter = 0;

        if(you.id == 1) {
            System.out.println("MOZESZ ZABIC PRZECIWNIKOWI 3 JEDNOSTKI!!!");
            System.out.println(board);
            for (int i = 0; i < 5; i++) {
                if (!board.empty(opponent.id, i))
                    System.out.print("  (" + i + ")  ");
            }
            System.out.println();

            Scanner scan = new Scanner(System.in);
            int number;
            //zabijanie 3 jednostek lub jeśli przeciwnik ma mniej niż 3 - wszystkich
            while (dead_counter < 3 && opponent.counter > 0) {
                System.out.print("wybierz: ");
                number = scan.nextInt();
                if (!board.empty(opponent.id, number) && number >= 0 && number < 5) {
                    discardeds.putCard(board.removeCard(opponent.id, number));
                    dead_counter++;
                    opponent.counter--;
                }
            }
        }
        else {
            String fromClient;
            //out.println("MOZESZ ZABIC PRZECIWNIKOWI 3 JEDNOSTKI!!!");
            //out.println(board);
            for (int i = 0; i < 5; i++) {
                //if (!board.empty(opponent.id, i))
                    //out.print("  (" + i + ")  ");
            }
            //out.println();

            int number;
            //zabijanie 3 jednostek lub jeśli przeciwnik ma mniej niż 3 - wszystkich
            while (dead_counter < 3 && opponent.counter > 0) {
                //out.println("5");
                fromClient = in.readLine();
                number = Integer.parseInt(fromClient);
                if (!board.empty(opponent.id, number) && number < 5) {
                    discardeds.putCard(board.removeCard(opponent.id, number));
                    dead_counter++;
                    opponent.counter--;
                }
            }
        }
    }
}
