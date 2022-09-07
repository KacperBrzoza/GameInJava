package com.example.Meat.Rage_Cards;

import com.example.Meat.Demo.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Selection extends R_Card{

    public Selection(){
        name = "Selection";
        description = "dobierajac stwora, dobierasz dwa. Jednego zatrzymujesz, drugiego odrzucasz (stale)";
    }

    public void effect(Player you, Player opponent, Board board, Discardeds_Stack discardeds, Cards_Stack cards, Money money, Rage_Cards rage, BufferedWriter out, BufferedReader in) throws IOException {
        you.Selection = 1;
    }
}
