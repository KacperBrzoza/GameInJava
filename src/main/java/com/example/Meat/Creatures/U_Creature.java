package com.example.Meat.Creatures;

//dopóki ta jednostka żyje stwory na dwóch polach za nią są nieśmiertelne
public class U_Creature extends Creature{

    public U_Creature(int cost, int attack, int hp){
        this.cost = cost;
        this.attack = attack;
        this.hp = hp;
        this.power = "U";
        this.Swarm = 0;
        this.Unbroaken = 0;
        this.poisoned = 0;
    }
}
