package Rage_Cards;

import Demo.*;

//przeciwnik natychmiast traci tarczę i nie dostaje karty Rage
public class Counterpunch extends R_Card{

    public Counterpunch(){
        this.name = "Counterpunch";
        this.description = "przeciwnik traci tarczę i nie dobiera karty Rage (jednorazowa)";
    }

    public void effect(Player you, Player opponent, Board board, Discardeds_Stack discardeds, Cards_Stack cards, Money money, Rage_Cards rage){
        if(opponent.showShields() > 0)
            opponent.loseShield();
    }

}
