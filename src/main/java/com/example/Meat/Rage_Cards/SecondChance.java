package com.example.Meat.Rage_Cards;

import com.example.Meat.Demo.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class SecondChance extends R_Card{
    public SecondChance(){
        name = "Second Chance";
        description = "dobierajac zeton waluty dobierasz dwa. Jeden zatrzymujesz, drugi odrzucasz (stale)";
    }

    public void effect(Player you, Player opponent, Board board, Discardeds_Stack discardeds, Cards_Stack cards, Money money, Rage_Cards rage, PrintWriter out, BufferedReader in) throws IOException {
        you.SecondChance = 1;
    }
}
