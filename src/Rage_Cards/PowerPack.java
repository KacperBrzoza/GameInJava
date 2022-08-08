package Rage_Cards;

import Demo.*;

//gracz natychmiast dobiera 2 żetony waluty, 2 karty stworów oraz uzdrawia 2 jednostki
public class PowerPack extends R_Card{
    public PowerPack(){
        this.name = "Power Pack";
        this.description = "dobierasz 2 żetony waluty, 2 karty stworów i uzdrawiasz dwie jednostki (jednorazowa)";
    }

    public void effect(Player you, Player opponent, Board board, Discardeds_Stack discardeds, Cards_Stack cards, Money money, Rage_Cards rage){
        for (int i = 0; i < 2; i++){
            you.eq.addCreature(cards.giveCard());
        }
        for (int i = 0; i < 2; i++){
            you.money += money.giveMoney(you, opponent);
        }
        if(!discardeds.empty()) {
            for (int i = 0; i < 2; i++) {
                you.eq.addCreature(discardeds.takeCard());
                if(discardeds.empty())
                    break;
            }
        }
    }
}
