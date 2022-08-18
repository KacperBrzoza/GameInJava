package com.example.Meat.Rage_Cards;

import com.example.Meat.Demo.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Crusher extends R_Card{
    public Crusher(){
        name = "Crusher";
        description = "mozesz niszczyc jednostki z obrona rowna ataku twoich stworow (stale)";
    }

    public void effect(Player you, Player opponent, Board board, Discardeds_Stack discardeds, Cards_Stack cards, Money money, Rage_Cards rage, PrintWriter out, BufferedReader in) throws IOException {
        you.Crusher = 1;
    }
}
