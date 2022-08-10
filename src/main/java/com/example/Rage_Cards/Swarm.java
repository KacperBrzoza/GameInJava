package Rage_Cards;

import Demo.*;

public class Swarm extends R_Card{

    public Swarm(){
        name = "Swarm";
        description = "+1 do ataku dla stworów z atakiem = 2 (stałe)";
    }

    public void effect(Player you, Player opponent, Board board, Discardeds_Stack discardeds, Cards_Stack cards, Money money, Rage_Cards rage){
        you.Swarm = 1;
    }
}
