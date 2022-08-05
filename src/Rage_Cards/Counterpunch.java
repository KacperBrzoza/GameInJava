package Rage_Cards;

import Demo.*;

//przeciwnik natychmiast traci tarczę i nie dostaje karty Rage
public class Counterpunch extends R_Card{

    public Counterpunch(){
        this.name = "Counterpunch";
        this.description = "przeciwnik traci tarczę i nie dobiera karty Rage (jednorazowa)";
    }

    public void effect(Player p1, Player p2, Board board, Discardeds_Stack discardeds, Cards_Stack cards, Money money){
        if(p2.showShields() > 0)
            p2.loseShield();
    }

}
