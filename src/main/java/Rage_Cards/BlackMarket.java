package Rage_Cards;

import Demo.*;

public class BlackMarket extends R_Card{
    public BlackMarket(){
        name = "Black Market";
        description = "pozwala raz na ture sprzedac 1 stwora zamiast wystawiać jednostki (stale)";
    }

    public void effect(Player you, Player opponent, Board board, Discardeds_Stack discardeds, Cards_Stack cards, Money money, Rage_Cards rage){
        you.BlackMarket = 1;
    }
}