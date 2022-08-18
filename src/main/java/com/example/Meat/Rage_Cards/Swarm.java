package com.example.Meat.Rage_Cards;

import com.example.Meat.Demo.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Swarm extends R_Card{

    public Swarm(){
        name = "Swarm";
        description = "+1 do ataku dla stworow z atakiem = 2 (stale)";
    }

    public void effect(Player you, Player opponent, Board board, Discardeds_Stack discardeds, Cards_Stack cards, Money money, Rage_Cards rage, PrintWriter out, BufferedReader in) throws IOException {
        you.Swarm = 1;
    }
}
