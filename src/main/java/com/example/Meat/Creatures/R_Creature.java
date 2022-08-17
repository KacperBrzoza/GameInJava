package com.example.Meat.Creatures;

//gdy inny stwór ma zginąć, ten zginie za niego
public class R_Creature extends Creature{

    public R_Creature(int cost, int attack, int hp){
        this.cost = cost;
        this.attack = attack;
        this.hp = hp;
        this.power = "R";
        this.Swarm = 0;
        this.Unbroaken = 0;
        this.poisoned = 0;
    }
}
