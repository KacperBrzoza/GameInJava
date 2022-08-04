package Rage_Cards;

import Demo.Discardeds_Stack;

import Demo.Money;
import Demo.Player;

public abstract class R_Card{

    public String name;

    public void effect(Player p, Discardeds_Stack discarded){}
    public void effect(Player p, Money money){}

    @Override
    public String toString(){
        return this.name;
    }
}
