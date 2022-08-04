package Rage_Cards;

import Demo.Discardeds_Stack;
import Demo.Player;

public class Resurrection extends R_Card{

    public Resurrection(){
        this.name = "Resurrection";
    }

    public void effect(Player p, Discardeds_Stack discarded){
        for (int i = 0; i < 5; i++){
            p.eq.addCreature(discarded.takeCard());
        }
    }
}
