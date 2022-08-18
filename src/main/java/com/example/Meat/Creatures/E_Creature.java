package com.example.Meat.Creatures;

import com.example.Meat.Demo.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//przy wystawieniu tego stwora możesz odrazu wystawić kolejnego z mocą E
public class E_Creature extends Creature{

    public E_Creature(int cost, int attack, int hp){
        this.cost = cost;
        this.attack = attack;
        this.hp = hp;
        this.power = "E";
        this.Swarm = 0;
        this.Unbroaken = 0;
        this.poisoned = 0;
    }

    @Override
    public void effect(Player you, Player opponent, Cards_Stack cards, Discardeds_Stack discardeds, Money money, Board board, PrintWriter out, BufferedReader in) throws IOException {
        //moc zadziała gdy gracz ma coś w ekwipunku
        if(you.eq.size() > 0){
            //wariant dla gracza 1
            if (you.id == 1) {
                //jeżeli pierwszy gracz ma jakieś stwory z mocą E w ewkipunku i nie ma 4 jednostek na planszy...
                //...wybierz takie jednostki
                List<Creature> e_creatures = new ArrayList<>();
                if (you.eq.size() > 0) {
                    for (int i = 0; i < you.eq.size(); i++) {
                        if (you.eq.ifE(i)) {
                        e_creatures.add(you.eq.copy(i));
                        }
                    }
                }
                //jeżeli aktualny gracz ma jakieś stwory z mocą E i nie ma 4 jednostek na planszy
                if (e_creatures.size() != 0 && you.counter < 4) {

                    System.out.println("Mozesz za darmo wystawic jednostke z moca E");
                    System.out.println(board);
                    for (int i = 0; i < e_creatures.size(); i++)
                        System.out.println("(" + i + ") " + e_creatures.get(i));
                    System.out.println("(" + e_creatures.size() + ") nie uzywaj mocy E");

                    int number = -1;
                    Scanner scan = new Scanner(System.in);
                    while (number < 0 || number > e_creatures.size()) {
                        System.out.print("wybierz: ");
                        number = scan.nextInt();

                        //jeżeli grac zwybrał postać z mocą E
                        if (number >= 0 && number < e_creatures.size()) {
                            Creature creature = e_creatures.get(number);
                            //dodatkowe cechy jeśli gracz posiada odpowiednie karty Rage
                            if (you.Swarm == 1) {
                                creature.setSwarm(1);
                                creature.increaseAttack();
                            }
                            if (you.Unbroaken == 1) {
                                creature.setUnbroaken(1);
                                creature.increaseHp();
                            }
                            //wystawienie na plansze wybraną jednostkę
                            board.put(creature, you, opponent, discardeds);
                            e_creatures.remove(number);
                            //usunięcie jej z ekwipunku
                            for (int i = 0; i < you.eq.size(); i++) {
                                if (you.eq.copy(i).theSame(creature)) {
                                    you.eq.pickCreature(i);
                                    break;
                                }
                            }
                            you.counter++;
                            creature.effect(you, opponent, cards, discardeds, money, board, out, in);
                            break;
                        }
                        //rezygnacja z mocy
                        else if (number == you.eq.size())
                            break;
                    }
                }
            }
            //wariant dla gracza 2
            else{
                String fromClient;

                //jeżeli drugi gracz ma jakieś stwory z mocą E w ewkipunku i nie ma 4 jednostek na planszy...
                //...wybierz takie jednostki
                List<Creature> e_creatures = new ArrayList<>();
                if (you.eq.size() > 0) {
                    for (int i = 0; i < you.eq.size(); i++) {
                        if (you.eq.ifE(i)) {
                         e_creatures.add(you.eq.copy(i));
                        }
                    }
                }
                //jeżeli aktualny gracz ma jakieś stwory z mocą E i nie ma 4 jednostek na planszy
                if (e_creatures.size() != 0 && you.counter < 4) {

                    out.println("Mozesz za darmo wystawic jednostke z moca E");
                    out.println(board);
                    for (int i = 0; i < e_creatures.size(); i++)
                        out.println("(" + i + ") " + e_creatures.get(i));
                    out.println("(" + e_creatures.size() + ") nie uzywaj mocy E");
                    out.println(e_creatures.size());

                    fromClient = in.readLine();
                    int number = Integer.parseInt(fromClient);
                    //jeśli otrzymaną liczbą był stwór z mocą E, to wystaw go
                    if (number != e_creatures.size()) {
                        Creature creature = e_creatures.get(number);
                        //dodatkowe cechy jeśli gracz posiada odpowiednie karty Rage
                        if (you.Swarm == 1) {
                            creature.setSwarm(1);
                            creature.increaseAttack();
                        }
                        if (you.Unbroaken == 1) {
                            creature.setUnbroaken(1);
                            creature.increaseHp();
                        }
                        //wystawienie na plansze wybraną jednostkę
                        board.put(creature, you, opponent, discardeds);
                        e_creatures.remove(number);
                        //usunięcie jej z ekwipunku
                        for (int i = 0; i < you.eq.size(); i++) {
                            if (you.eq.copy(i).theSame(creature)) {
                                you.eq.pickCreature(i);
                                break;
                            }
                        }
                        you.counter++;
                        creature.effect(you, opponent, cards, discardeds, money, board, out, in);
                    }
                }
            }
        }
    }
}
