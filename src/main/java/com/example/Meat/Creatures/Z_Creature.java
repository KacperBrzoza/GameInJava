package com.example.Meat.Creatures;

import com.example.Meat.Demo.*;

import java.util.Scanner;

//gdy wystawiasz tego stwora możesz zamienić go miejscem z sojuszniczym stworem
public class Z_Creature extends Creature{
    public Z_Creature(int cost, int attack, int hp){
        this.cost = cost;
        this.attack = attack;
        this.hp = hp;
        this.power = "Z";
        this.Swarm = 0;
        this.Unbroaken = 0;
        this.poisoned = 0;
    }

    @Override
    public void effect(Player you, Player opponent, Cards_Stack cards, Discardeds_Stack discardeds, Money money, Board board) {
        //jeżeli masz już coś wystawione
        if(you.counter > 1){
            System.out.println("Mozesz zamienic innego swojego stwora z tym miejscami");
            System.out.println(board);

            //wypisuje pola do wyboru w zależności od gracza
            if(you.id == 1) {
                for (int i = 1; i < 5; i++) {
                    if (!board.empty(you.id, i))
                        System.out.print(" (" + i + ")  ");
                }
            }
            else {
                for (int i = 0; i < 4; i++) {
                    if (!board.empty(you.id, i))
                        System.out.print(" (" + i + ")  ");
                }
            }
            System.out.println("\n(5) - nie zamieniaj");

            Scanner scan = new Scanner(System.in);
            int number = -1;
            while (number < 0){
                System.out.print("wybierz: ");
                number = scan.nextInt();

                //jeśli gracz wybrał właściwą jednostkę dochodzi do zamiany oraz zamieniane jednostki toczą dodatkowe walki
                if((number >= 1 && number < 4) || (number == 0 && you.id == 2) || (number == 4 && you.id == 1)){
                    if(!board.empty(you.id, number)){
                        int startowe;
                        if(you.id == 1)
                            startowe = 0;
                        else
                            startowe = 4;
                        Creature buf = board.getCreature(you.id, number);
                        board.setCreature(you.id, number, board.getCreature(you.id, startowe));
                        board.setCreature(you.id, startowe, buf);
                        board.fight(you, opponent, startowe, discardeds);
                        board.fight(you, opponent, number, discardeds);
                    }
                    else
                        number = -1;
                }
                else if(number == 5)
                    break;
                else
                    number = -1;
            }
        }
    }
}
