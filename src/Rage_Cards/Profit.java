package Rage_Cards;

import Demo.*;

//gracz natychmiast dobiera 5 żetonów waluty
public class Profit extends R_Card{
    public Profit(){
        this.name = "Profit";
        this.description = "dobierasz 5 żetonów waluty (jednorazowa)";
    }

    public void effect(Player p2, Player p1, Board board, Discardeds_Stack discardeds, Cards_Stack cards, Money money){
        for (int i = 0; i < 5; i++){
            p2.money += money.giveMoney();
        }
    }
}
