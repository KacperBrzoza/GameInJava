package com.example.Meat.Rage_Cards;

import com.example.Meat.Demo.*;

public class RatCatcher extends R_Card{
    public RatCatcher(){
        name = "Rat Catcher";
        description = "na koniec tury dobierasz karte stwora (stale)";
    }

    public void effect(Player you, Player opponent, Board board, Discardeds_Stack discardeds, Cards_Stack cards, Money money, Rage_Cards rage){
        you.RatCatcher = 1;
    }
}
