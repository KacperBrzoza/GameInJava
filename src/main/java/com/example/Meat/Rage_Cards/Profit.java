package com.example.Meat.Rage_Cards;

import com.example.Meat.Demo.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;

//gracz natychmiast dobiera 5 żetonów waluty
public class Profit extends R_Card{
    public Profit(){
        this.name = "Profit";
        this.description = "dobierasz 5 zetonow waluty (jednorazowa)";
    }

    public void effect(Player you, Player opponent, Board board, Discardeds_Stack discardeds, Cards_Stack cards, Money money, Rage_Cards rage, BufferedWriter out, BufferedReader in) throws IOException {
        for (int i = 0; i < 5; i++){
            you.money += money.giveMoney(you, opponent);
        }
    }
}
