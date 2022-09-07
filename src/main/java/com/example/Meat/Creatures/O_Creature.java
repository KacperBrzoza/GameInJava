package com.example.Meat.Creatures;

import com.example.Meat.Demo.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

//gdy wystawisz tego stwora, możesz odrazu wystawić kolejnego za darmo
public class O_Creature extends Creature{
    public O_Creature(int cost, int attack, int hp){
        this.cost = cost;
        this.attack = attack;
        this.hp = hp;
        this.power = "O";
        this.Swarm = 0;
        this.Unbroaken = 0;
        this.poisoned = 0;
    }

    @Override
    public void effect(Player you, Player opponent, Cards_Stack cards, Discardeds_Stack discardeds, Money money, Board board, BufferedWriter out, BufferedReader in) throws IOException {
        if(you.id == 1){
            //jeżeli pierwszy gracz ma coś w ekwipunku i nie ma 4 jednostek na planszy
            if(you.eq.size() > 0 && you.counter < 4){
                System.out.println("Mozesz za darmo wystawic dowolna jednostke");
                System.out.println(board);
                System.out.println(you.eq);
                System.out.println("(" + you.eq.size() + ") nie uzywaj mocy O");

                int number = -1;
                Scanner scan = new Scanner(System.in);
                while (number < 0 || number > you.eq.size()) {
                    System.out.print("wybierz: ");
                    number = scan.nextInt();

                    //jeżeli gracz wybrał jednostkę ze swojego ekwipunku
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
                        board.put(creature, you, opponent, discardeds);
                        you.counter++;
                        creature.effect(you, opponent, cards, discardeds, money, board, out, in);
                        break;
                    } else if (number == you.eq.size())
                        break;
                }
            }
        }
        else{
            String fromCLient;
            //jeżeli drugi gracz ma coś w ekwipunku i nie ma 4 jednostek na planszy
            if(you.eq.size() > 0 && you.counter < 4){
                /*
                out.println("Mozesz za darmo wystawic dowolna jednostke");
                out.println(board);
                out.println(you.eq);
                out.println("(" + you.eq.size() + ") nie uzywaj mocy O");

                out.println(you.eq.size());
                 */
                fromCLient = in.readLine();
                int number = Integer.parseInt(fromCLient);
                //jeżeli gracz wybrał jednostkę ze swojego ekwipunku
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
                    board.put(creature, you, opponent, discardeds);
                    you.counter++;
                    creature.effect(you, opponent, cards, discardeds, money, board, out, in);
                }
            }
        }
    }
}
