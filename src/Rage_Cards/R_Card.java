package Rage_Cards;

import Demo.*;

//klasa abstrakcyjna ogólnie kart Rage
public abstract class R_Card{

    protected String name;
    protected String description;

    //na tej metodzie "do przeciążania" opierają się karty Rage
    public void effect(Player p1, Player p2, Board board, Discardeds_Stack discardeds, Cards_Stack cards, Money money){}

    @Override
    public String toString(){
        return this.name;
    }

    public String getDescription(){
        return this.name + " - " + this.description;
    }
}
