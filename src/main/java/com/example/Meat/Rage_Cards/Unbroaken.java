package com.example.Meat.Rage_Cards;

import com.example.Main.Game.GameController;
import com.example.Meat.Demo.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Unbroaken extends R_Card{
    public Unbroaken(){
        name = "Unbroaken";
        description = "+1 do obrony dla stworów z obroną = 2 (stale)";
        this.path = "src/main/resources/img/RAGE_powers/unbroaken.png";
    }

    public void effect(Player you, Player opponent, Board board, Discardeds_Stack discardeds, Cards_Stack cards, Money money, Rage_Cards rage, BufferedWriter out, BufferedReader in, GameController gameController) throws IOException {
        you.Unbroaken = 1;
    }
}
