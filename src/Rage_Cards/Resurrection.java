package Rage_Cards;

import Demo.*;

//natychmiast uzdrawia 5 stworów
public class Resurrection extends R_Card{

    public Resurrection(){
        this.name = "Resurrection";
        this.description = "uzdrawiasz 5 kart stworów (jednorazowa)";
    }

    public void effect(Player p1, Player p2, Board board, Discardeds_Stack discardeds, Cards_Stack cards, Money money){
        if(!discardeds.empty()) {
            for (int i = 0; i < 5; i++) {
                p1.eq.addCreature(discardeds.takeCard());
                if(discardeds.empty())
                    break;
            }
        }
    }
}
