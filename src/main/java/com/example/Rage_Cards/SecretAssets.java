package Rage_Cards;

import Demo.*;

public class SecretAssets extends R_Card{

    public SecretAssets(){
        name = "Secret Assets";
        description = "na końcu każdej tury dobierasz 1 żeton waluty (stałe)";
    }

    public void effect(Player you, Player opponent, Board board, Discardeds_Stack discardeds, Cards_Stack cards, Money money, Rage_Cards rage){
        you.SecretAssets = 1;
    }
}
