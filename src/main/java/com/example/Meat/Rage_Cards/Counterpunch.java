package com.example.Meat.Rage_Cards;

import com.example.Meat.Demo.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

//przeciwnik natychmiast traci tarczę i nie dostaje karty Rage (ta karta nie wpływa na punkty graczy)
public class Counterpunch extends R_Card{

    public Counterpunch(){
        this.name = "Counterpunch";
        this.description = "przeciwnik traci tarcze i nie dobiera karty Rage (jednorazowa)";
    }

    public void effect(Player you, Player opponent, Board board, Discardeds_Stack discardeds, Cards_Stack cards, Money money, Rage_Cards rage, PrintWriter out, BufferedReader in) throws IOException {
        if(opponent.showShields() > 0)
            opponent.loseShield();
    }

}
