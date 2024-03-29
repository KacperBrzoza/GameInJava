package com.example.Meat.Creatures;

import com.example.Main.Game.GameController;
import com.example.Meat.Demo.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.PrintWriter;

//gdy wystawisz tego stwora, dobierasz stwora ze stosu kart odrzuconych
public class H_Creature extends Creature{
    public H_Creature(int cost, int attack, int hp, String path){
        this.cost = cost;
        this.attack = attack;
        this.hp = hp;
        this.power = "H";
        this.Swarm = 0;
        this.Unbroaken = 0;
        this.poisoned = 0;
        this.path = path;
    }

    @Override
    public void effect(Player you, Player opponent, Cards_Stack cards, Discardeds_Stack discardeds, Money money, Board board, BufferedWriter out, BufferedReader in, GameController gameController) {
        if(!discardeds.empty()) {
            you.eq.addCreature(discardeds.takeCard());
            if(you.id == 1)
                System.out.println("Moc H dobrala ostatnio odrzucona karte");
            //else
                //out.println("Moc H dobrala ostatnio odrzucona karte");
        }
    }
}
