package Demo;

import Rage_Cards.R_Card;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//ta klasa spełnia dwie role:
//1. Ma służyć jako "stos" kart Rage do dobrania
//2. Ma pozwalać graczom posiadać swoje karty Rage
public class Rage_Cards {
    private final List<R_Card> cards;

    //tu będzie inicjalizacja stosu kart Rage
    public void initialization(){};

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
        String my_cards = "";
        for(int i = 0; i < cards.size(); i++)
        {
            my_cards += cards.get(i) + "\n";
        }
        return my_cards;
    }

}
