package com.example.Meat.Rage_Cards;

import com.example.Meat.Demo.*;

//przeciwnik natychmiast traci tarczę i nie dostaje karty Rage (ta karta nie wpływa na punkty graczy)
public class Counterpunch extends R_Card{

    public Counterpunch(){
        this.name = "Counterpunch";
        this.description = "przeciwnik traci tarcze i nie dobiera karty Rage (jednorazowa)";
    }

    public void effect(Player you, Player opponent, Board board, Discardeds_Stack discardeds, Cards_Stack cards, Money money, Rage_Cards rage){
        if(opponent.showShields() > 0)
            opponent.loseShield();
    }

}
