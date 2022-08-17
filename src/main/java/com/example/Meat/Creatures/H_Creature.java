package com.example.Meat.Creatures;

import com.example.Meat.Demo.*;

//gdy wystawisz tego stwora, dobierasz stwora ze stosu kart odrzuconych
public class H_Creature extends Creature{
    public H_Creature(int cost, int attack, int hp){
        this.cost = cost;
        this.attack = attack;
        this.hp = hp;
        this.power = "H";
        this.Swarm = 0;
        this.Unbroaken = 0;
        this.poisoned = 0;
    }

    @Override
    public void effect(Player you, Player opponent, Cards_Stack cards, Discardeds_Stack discardeds, Money money, Board board) {
        if(!discardeds.empty()) {
            you.eq.addCreature(discardeds.takeCard());
            System.out.println("Moc H dobrala ostatnio odrzucona karte");
        }
    }
}
