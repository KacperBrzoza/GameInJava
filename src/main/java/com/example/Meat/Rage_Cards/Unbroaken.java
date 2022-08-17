package Rage_Cards;

import Demo.*;

public class Unbroaken extends R_Card{
    public Unbroaken(){
        name = "Unbroaken";
        description = "+1 do obrony dla stworów z obroną = 2 (stale)";
    }

    public void effect(Player you, Player opponent, Board board, Discardeds_Stack discardeds, Cards_Stack cards, Money money, Rage_Cards rage){
        you.Unbroaken = 1;
    }
}
