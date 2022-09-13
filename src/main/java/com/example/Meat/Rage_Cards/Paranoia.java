package com.example.Meat.Rage_Cards;

import com.example.Main.Game.GameController;
import com.example.Meat.Creatures.Creature;
import com.example.Meat.Demo.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class Paranoia extends R_Card{

    public Paranoia(){
        name = "Common Fear";
        description = "wszystkie wrogie jednostki wracaja do wlasciciela";
        this.path = "src/main/resources/img/RAGE_powers/paranoia.png";
    }

    public void effect(Player you, Player opponent, Board board, Discardeds_Stack discardeds, Cards_Stack cards, Money money, Rage_Cards rage, BufferedWriter out, BufferedReader in, GameController gameController) throws IOException {

        //jeżeli przeciwnik ma coś wystawione
        if(opponent.counter > 0){
            //cofnięcie kart przeciwnika
            for(int i = 4; i >=0; i--){
                if(!board.empty(opponent.id, i)) {
                    Creature creature = board.removeCard(opponent.id, 4);
                    opponent.eq.addCreature(creature);
                    opponent.counter--;
                }
            }
            System.out.println(board);
            //out.println(board);
        }
    }
}
