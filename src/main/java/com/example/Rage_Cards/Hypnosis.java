package Rage_Cards;

import Demo.*;

public class Hypnosis extends R_Card{
    public Hypnosis(){
        name = "Hypnosis";
        description = "na koniec tury dobierasz kartę stwora (stałe)";
    }

    public void effect(Player you, Player opponent, Board board, Discardeds_Stack discardeds, Cards_Stack cards, Money money, Rage_Cards rage){
        you.Hypnosis = 1;
    }
}
