package com.example.Meat.Rage_Cards;

import com.example.Meat.Demo.*;

public class Crusher extends R_Card{
    public Crusher(){
        name = "Crusher";
        description = "mozesz niszczyc jednostki z obrona rowna ataku twoich stworow (stale)";
    }

    public void effect(Player you, Player opponent, Board board, Discardeds_Stack discardeds, Cards_Stack cards, Money money, Rage_Cards rage){
        you.Crusher = 1;
    }
}
