package Rage_Cards;

import Demo.*;

//gracz natychmiast dobiera 5 kart stworów
public class Recruiting extends R_Card{
    public Recruiting(){
        this.name = "Recruiting";
        this.description = "dobierasz 5 kart stworow (jednorazowa)";
    }

    public void effect(Player you, Player opponent, Board board, Discardeds_Stack discardeds, Cards_Stack cards, Money money, Rage_Cards rage){
        for (int i = 0; i < 5; i++){
            you.eq.addCreature(cards.giveCard());
        }
    }
}
