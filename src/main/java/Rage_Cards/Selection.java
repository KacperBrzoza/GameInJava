package Rage_Cards;

import Demo.*;

public class Selection extends R_Card{

    public Selection(){
        name = "Selection";
        description = "dobierając stwora, dobierasz dwa. Jednego zatrzymujesz, drugiego odrzucasz (stałe)";
    }

    public void effect(Player you, Player opponent, Board board, Discardeds_Stack discardeds, Cards_Stack cards, Money money, Rage_Cards rage){
        you.Selection = 1;
    }
}
