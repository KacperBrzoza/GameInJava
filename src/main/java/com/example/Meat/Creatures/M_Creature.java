package com.example.Meat.Creatures;

import com.example.Meat.Demo.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.PrintWriter;

//gdy wystawisz tego stwora, dobierasz żeton waluty
public class M_Creature extends Creature{
    public M_Creature(int cost, int attack, int hp){
        this.cost = cost;
        this.attack = attack;
        this.hp = hp;
        this.power = "M";
        this.Swarm = 0;
        this.Unbroaken = 0;
        this.poisoned = 0;
    }

    @Override
    public void effect(Player you, Player opponent, Cards_Stack cards, Discardeds_Stack discardeds, Money money, Board board, BufferedWriter out, BufferedReader in) {
        you.money += money.giveMoney(you, opponent);
        if(you.id == 1)
            System.out.println("Moc M dobrala zeton waluty");
        //else
            //out.println("Moc M dobrala zeton waluty");
    }
}
