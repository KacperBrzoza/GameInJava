package com.example.Meat.Rage_Cards;

import com.example.Meat.Demo.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;

//gracz natychmiast dobiera 2 żetony waluty, 2 karty stworów oraz uzdrawia 2 jednostki
public class PowerPack extends R_Card{
    public PowerPack(){
        this.name = "Power Pack";
        this.description = "dobierasz 2 zetony waluty, 2 karty stworow i uzdrawiasz dwie jednostki (jednorazowa)";
    }

    public void effect(Player you, Player opponent, Board board, Discardeds_Stack discardeds, Cards_Stack cards, Money money, Rage_Cards rage, BufferedWriter out, BufferedReader in) throws IOException {
        for (int i = 0; i < 2; i++){
            you.eq.addCreature(cards.giveCard(out, you, opponent));
        }
        for (int i = 0; i < 2; i++){
            you.money += money.giveMoney(you, opponent);
        }
        if(!discardeds.empty()) {
            for (int i = 0; i < 2; i++) {
                you.eq.addCreature(discardeds.takeCard());
                if(discardeds.empty())
                    break;
            }
        }
    }
}
