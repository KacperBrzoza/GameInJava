package com.example.Meat.Rage_Cards;

import com.example.Main.Game.GameController;
import com.example.Meat.Demo.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Thief extends R_Card{
    public Thief(){
        name = "Thief";
        description = "po kazdym przetasowaniu zetonow waluty otrzymujesz 3 zetony (stale)";
        this.path = "src/main/resources/img/RAGE_powers/thief.png";
    }

    public void effect(Player you, Player opponent, Board board, Discardeds_Stack discardeds, Cards_Stack cards, Money money, Rage_Cards rage, BufferedWriter out, BufferedReader in, GameController gameController) throws IOException {
        you.Thief = 1;
    }
}
