package Rage_Cards;

import Demo.*;

public class Crusher extends R_Card{
    public Crusher(){
        name = "Crusher";
        description = "możesz niszczyć jednostki z obroną równą ataku twoich stworów (stałe)";
    }

    public void effect(Player you, Player opponent, Board board, Discardeds_Stack discardeds, Cards_Stack cards, Money money, Rage_Cards rage){
        you.Crusher = 1;
    }
}
