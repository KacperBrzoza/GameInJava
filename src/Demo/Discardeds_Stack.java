package Demo;

import Creatures.Creature;

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
        if(creature.ifUnbroaken()){
            creature.setUnbroaken(0);
            creature.decreaseHp();
        }
        discarded.add(creature);
    }

    //jeśli stos nie jest pusty, to zabiera pierwszą kartę z wierzchu i usuwa ją ze stosu
    public Creature takeCard(){
        if(discarded.size() == 0)
            return null;
        else {
            return discarded.pop();
        }
    }

    //metoda sprawdza czy stos kart odrzuconcyh jest pusty
    public boolean empty(){
        return discarded.size() == 0;
    }
}
