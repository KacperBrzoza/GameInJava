package com.example.Meat.Demo;

import com.example.Meat.Creatures.Creature;

import java.util.Stack;

//stos kart odrzuconych
public class Discardeds_Stack {

    Stack<Creature> discarded;

    public Discardeds_Stack(){
        discarded = new Stack<>();
    }

    //dokłada odrzuconą kartę na stos
    public void putCard(Creature creature){
        //obsługa zdejmowania cechy Swarm
        if(creature.ifSwarm()){
            creature.setSwarm(0);
            creature.decreaseAttack();
        }
        //obsługa zdejmowania cechy Unbroaken
        if(creature.ifUnbroaken()){
            creature.setUnbroaken(0);
            creature.decreaseHp();
        }
        discarded.add(creature);
    }

    //zwraca pierwszą kartę z wierzchu stosu i ją z niego usuwa
    public Creature takeCard(){
        return discarded.pop();
    }

    //metoda sprawdza czy stos kart odrzuconcyh jest pusty
    public boolean empty(){
        return discarded.size() == 0;
    }
}
