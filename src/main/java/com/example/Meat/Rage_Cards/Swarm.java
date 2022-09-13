package com.example.Meat.Rage_Cards;

import com.example.Main.Game.GameController;
import com.example.Meat.Demo.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Swarm extends R_Card{

    public Swarm(){
        name = "Swarm";
        description = "+1 do ataku dla stworow z atakiem = 2 (stale)";
        this.path = "src/main/resources/img/RAGE_powers/swarm.png";
    }

    public void effect(Player you, Player opponent, Board board, Discardeds_Stack discardeds, Cards_Stack cards, Money money, Rage_Cards rage, BufferedWriter out, BufferedReader in, GameController gameController) throws IOException {
        you.Swarm = 1;
    }
}
