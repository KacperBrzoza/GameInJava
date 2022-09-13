package com.example.Meat.Rage_Cards;

import com.example.Main.Game.GameController;
import com.example.Meat.Creatures.Creature;
import com.example.Meat.Demo.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

//pozwala natychmiast wystawić jednostkę
public class FinalWord extends R_Card{

    public FinalWord(){
        this.name = "Final Word";
        this.description = "natychmiast wystawiasz jednostke za darmo (jednorazowa)";
        this.path = "src/main/resources/img/RAGE_powers/finalword.png";
    }

    public void effect(Player you, Player opponent, Board board, Discardeds_Stack discardeds, Cards_Stack cards, Money money, Rage_Cards rage, BufferedWriter out, BufferedReader in, GameController gameController) throws IOException {
        if(you.id == 1){
            //jeżeli aktualny gracz ma coś w ekwipunku i nie ma 4 jednostek na planszy
            if(you.eq.size() > 0 && you.counter < 4) {

                System.out.println("\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n");
                System.out.println("WYSTAWIASZ TERAZ DARMOWEGO STWORA!!!");
                System.out.println(board);
                System.out.println(you.eq);

                int number = -1;
                Scanner scan = new Scanner(System.in);
                while (number < 0 || number >= you.eq.size()) {
                    System.out.print("wybierz: ");
                    number = scan.nextInt();
                    //tu następuje właściwy wybór
                    if (number >= 0 && number < you.eq.size()) {
                        Creature creature = you.eq.pickCreature(number);
                        //dodatkowe cechy jeśli gracz posiada odpowiednie karty Rage
                        if (you.Swarm == 1) {
                            creature.setSwarm(1);
                            creature.increaseAttack();
                        }
                        if (you.Unbroaken == 1) {
                            creature.setUnbroaken(1);
                            creature.increaseHp();
                        }
                        board.put(creature, you, opponent, discardeds, gameController);
                        you.counter++;
                        creature.effect(you, opponent, cards, discardeds, money, board, out, in, gameController);
                    }
                }
                System.out.println(board);
            }
        }
        else {
            String fromClient;
            //jeżeli aktualny gracz ma coś w ekwipunku i nie ma 4 jednostek na planszy
            if(you.eq.size() > 0 && you.counter < 4) {

                //out.println("\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n");
                //out.println("WYSTAWIASZ TERAZ DARMOWEGO STWORA!!!");
                //out.println(board);
                //out.println(you.eq);

                int number = -1;
                while (number < 0 || number >= you.eq.size()) {
                    //out.println(you.eq.size());
                    fromClient = in.readLine();
                    number = Integer.parseInt(fromClient);
                    //tu następuje właściwy wybór
                    if (number >= 0 && number < you.eq.size()) {
                        Creature creature = you.eq.pickCreature(number);
                        //dodatkowe cechy jeśli gracz posiada odpowiednie karty Rage
                        if (you.Swarm == 1) {
                            creature.setSwarm(1);
                            creature.increaseAttack();
                        }
                        if (you.Unbroaken == 1) {
                            creature.setUnbroaken(1);
                            creature.increaseHp();
                        }
                        board.put(creature, you, opponent, discardeds, gameController);
                        you.counter++;
                        creature.effect(you, opponent, cards, discardeds, money, board, out, in, gameController);
                    }
                }
                System.out.println(board);
                //out.println(board);
            }
        }
    }
}
