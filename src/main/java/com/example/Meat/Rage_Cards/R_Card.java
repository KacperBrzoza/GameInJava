package com.example.Meat.Rage_Cards;

import com.example.Meat.Demo.*;

//klasa abstrakcyjna ogólnie kart Rage
public abstract class R_Card{

    protected String name;
    protected String description;

    //na tej metodzie "do przeciążania" opierają się karty Rage
    public void effect(Player you, Player opponent, Board board, Discardeds_Stack discardeds, Cards_Stack cards, Money money, Rage_Cards rage){}

    @Override
    public String toString(){
        return this.name;
    }

    public String getDescription(){
        return this.name + " - " + this.description;
    }
}
