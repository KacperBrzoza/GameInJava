package Rage_Cards;

import Demo.*;

//natychmiast uzdrawia 5 stworów
public class Resurrection extends R_Card{

    public Resurrection(){
        this.name = "Resurrection";
        this.description = "uzdrawiasz 5 kart stworów (jednorazowa)";
    }

    public void effect(Player you, Player opponent, Board board, Discardeds_Stack discardeds, Cards_Stack cards, Money money, Rage_Cards rage){
        if(!discardeds.empty()) {
            for (int i = 0; i < 5; i++) {
                you.eq.addCreature(discardeds.takeCard());
                if(discardeds.empty())
                    break;
            }
        }
    }
}
