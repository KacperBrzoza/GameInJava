package Rage_Cards;

import Demo.*;

public class SecondChance extends R_Card{
    public SecondChance(){
        name = "Second Chance";
        description = "dobierając żeton waluty dobierasz dwa. Jeden zatrzymujesz, drugi odrzucasz (stałe)";
    }

    public void effect(Player you, Player opponent, Board board, Discardeds_Stack discardeds, Cards_Stack cards, Money money, Rage_Cards rage){
        you.SecondChance = 1;
    }
}
