package Rage_Cards;

import Demo.*;

public class Thief extends R_Card{
    public Thief(){
        name = "Thief";
        description = "po kazdym przetasowaniu zetonow waluty otrzymujesz 3 zetony (stale)";
    }

    public void effect(Player you, Player opponent, Board board, Discardeds_Stack discardeds, Cards_Stack cards, Money money, Rage_Cards rage){
        you.Thief = 1;
    }
}
