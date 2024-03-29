package com.example.Meat.Demo;

import com.example.Meat.Rage_Cards.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//ta klasa spełnia dwie role:
//1. Ma służyć jako "stos" kart Rage do dobrania
//2. Ma pozwalać graczom posiadać swoje karty Rage
public class Rage_Cards {
    private final List<R_Card> cards;

    //inicjalizacja stosu kart Rage
    public void initialization(){
        /*
        cards.add(new Resurrection());
        cards.add(new Profit());
        cards.add(new Recruiting());
        cards.add(new Countershot());
        cards.add(new FinalWord());
        cards.add(new PowerPack());
        cards.add(new Redeployment());
        cards.add(new Betrayal());
        cards.add(new Extermination());
        cards.add(new Paranoia());
        cards.add(new Weakness());
        cards.add(new SecretAssets());
        //cards.add(new Selection());       chwilowo wyłączona z użytku
        cards.add(new Thief());
        //cards.add(new BlackMarket());       chwilowo wyłączona z użytku
        //cards.add(new Swarm());           chwilowo wyłączona z użytku
        //cards.add(new SecondChance());      chwilowo wyłączona z użytku
        cards.add(new Crusher());
        //cards.add(new Unbroaken());       chwilowo wyłączona z użytku
        cards.add(new RatCatcher());*/

    }

    public Rage_Cards(){
        cards = new ArrayList<>();
    }

    //poniższa metoda losuje kartę Rage z tego "stosu" i ją zwraca
    //ma służyć do dobierania kart Rage przez graczy
    public R_Card giveCard(){
        Random rand = new Random();
        int n = rand.nextInt(cards.size());
        R_Card card = cards.get(n);
        cards.remove(n);
        return card;
    }

    //umieszcza kartę Rage na liście
    public void putCard(R_Card card){
        cards.add(card);
    }


    //wypisuje wszystkie karty Rage z listy
    @Override
    public String toString(){
        StringBuilder my_cards = new StringBuilder();
        for (R_Card card : cards) {
            my_cards.append(card.getDescription()).append("\n");
        }
        return my_cards.toString();
    }
}
