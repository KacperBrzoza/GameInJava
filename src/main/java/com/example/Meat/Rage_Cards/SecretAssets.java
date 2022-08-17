package Rage_Cards;

import Demo.*;

public class SecretAssets extends R_Card{

    public SecretAssets(){
        name = "Secret Assets";
        description = "na koncu kazdej tury dobierasz 1 zeton waluty (stale)";
    }

    public void effect(Player you, Player opponent, Board board, Discardeds_Stack discardeds, Cards_Stack cards, Money money, Rage_Cards rage){
        you.SecretAssets = 1;
    }
}
