package Rage_Cards;

import Demo.*;

//gracz natychmiast dobiera 5 żetonów waluty
public class Profit extends R_Card{
    public Profit(){
        this.name = "Profit";
        this.description = "dobierasz 5 żetonów waluty (jednorazowa)";
    }

    public void effect(Player you, Player opponent, Board board, Discardeds_Stack discardeds, Cards_Stack cards, Money money, Rage_Cards rage){
        for (int i = 0; i < 5; i++){
            you.money += money.giveMoney();
        }
    }
}
