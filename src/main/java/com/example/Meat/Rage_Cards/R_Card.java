package com.example.Meat.Rage_Cards;

import com.example.Main.Game.GameController;
import com.example.Meat.Demo.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;

//klasa abstrakcyjna ogólnie kart Rage
public abstract class R_Card{

    protected String name;
    protected String description;
    public String path;

    //na tej metodzie "do przeciążania" opierają się karty Rage
    public void effect(Player you, Player opponent, Board board, Discardeds_Stack discardeds, Cards_Stack cards, Money money, Rage_Cards rage, BufferedWriter out, BufferedReader in, GameController gameController) throws IOException {}

    @Override
    public String toString(){
        return this.name;
    }

    public String getDescription(){
        return this.name + " - " + this.description;
    }
}
