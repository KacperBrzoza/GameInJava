package com.example.Meat.Rage_Cards;

import com.example.Meat.Demo.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class BlackMarket extends R_Card{
    public BlackMarket(){
        name = "Black Market";
        description = "pozwala raz na ture sprzedac 1 stwora zamiast wystawiać jednostki (stale)";
    }

    public void effect(Player you, Player opponent, Board board, Discardeds_Stack discardeds, Cards_Stack cards, Money money, Rage_Cards rage, BufferedWriter out, BufferedReader in) throws IOException {
        you.BlackMarket = 1;
    }
}
