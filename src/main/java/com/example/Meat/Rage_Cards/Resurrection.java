package com.example.Meat.Rage_Cards;

import com.example.Main.Game.GameController;
import com.example.Meat.Demo.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;

//natychmiast uzdrawia 5 stworów
public class Resurrection extends R_Card{

    public Resurrection(){
        this.name = "Resurrection";
        this.description = "uzdrawiasz 5 kart stworow (jednorazowa)";
        this.path = "src/main/resources/img/RAGE_powers/resurrection.png";
    }

    public void effect(Player you, Player opponent, Board board, Discardeds_Stack discardeds, Cards_Stack cards, Money money, Rage_Cards rage, BufferedWriter out, BufferedReader in, GameController gameController) throws IOException {
        if(!discardeds.empty()) {
            for (int i = 0; i < 5; i++) {
                you.eq.addCreature(discardeds.takeCard());
                if(discardeds.empty())
                    break;
            }
        }
    }
}
