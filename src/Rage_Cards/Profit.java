package Rage_Cards;

import Demo.Money;
import Demo.Player;

public class Profit extends R_Card{
    public Profit(){
        this.name = "Profit";
    }

    public void effect(Player p, Money money){
        for (int i = 0; i < 5; i++){
            p.money += money.giveMoney();
        }
    }
}
