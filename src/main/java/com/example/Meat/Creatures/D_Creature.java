package com.example.Meat.Creatures;

import com.example.Meat.Demo.*;

//gdy wystawisz tego stwora, dobierasz kartę stwora
public class D_Creature extends Creature{

    public D_Creature(int cost, int attack, int hp){
        this.cost = cost;
        this.attack = attack;
        this.hp = hp;
        this.power = "D";
        this.Swarm = 0;
        this.Unbroaken = 0;
        this.poisoned = 0;
    }

    @Override
    public void effect(Player you, Player opponent, Cards_Stack cards, Discardeds_Stack discardeds, Money money, Board board) {
        you.eq.addCreature(cards.giveCard());
        System.out.println("Moc D dobrala karte");
    }
}
