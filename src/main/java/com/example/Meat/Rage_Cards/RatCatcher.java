package com.example.Meat.Rage_Cards;

import com.example.Main.Game.GameController;
import com.example.Meat.Demo.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class RatCatcher extends R_Card{
    public RatCatcher(){
        name = "Rat Catcher";
        description = "na koniec tury dobierasz karte stwora (stale)";
        this.path = "src/main/resources/img/RAGE_powers/ratcatcher.png";
    }

    public void effect(Player you, Player opponent, Board board, Discardeds_Stack discardeds, Cards_Stack cards, Money money, Rage_Cards rage, BufferedWriter out, BufferedReader in, GameController gameController) throws IOException {
        you.RatCatcher = 1;
    }
}
