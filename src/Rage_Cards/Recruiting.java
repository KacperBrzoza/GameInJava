package Rage_Cards;

import Demo.*;

//gracz natychmiast dobiera 5 kart stworów
public class Recruiting extends R_Card{
    public Recruiting(){
        this.name = "Recruiting";
        this.description = "dobierasz 5 kart stworów (jednorazowa)";
    }

    public void effect(Player p2, Player p1, Board board, Discardeds_Stack discardeds, Cards_Stack cards, Money money){
        for (int i = 0; i < 5; i++){
            p2.eq.addCreature(cards.giveCard());
        }
    }
}
