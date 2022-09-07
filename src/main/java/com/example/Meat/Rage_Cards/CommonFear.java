package com.example.Meat.Rage_Cards;

import com.example.Meat.Creatures.Creature;
import com.example.Meat.Demo.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class CommonFear extends R_Card{

    public CommonFear(){
        name = "Common Fear";
        description = "wszystkie wrogie jednostki wracaja do wlasciciela";
    }

    public void effect(Player you, Player opponent, Board board, Discardeds_Stack discardeds, Cards_Stack cards, Money money, Rage_Cards rage, BufferedWriter out, BufferedReader in) throws IOException {

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
