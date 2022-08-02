package Demo;

import Creatures.Creature;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//stos kart stworów
public class Cards_Stack {

    private final List<Creature> creatures;

    //inicjalizacja "stosu" kart stworów, to tu będą ręcznie wprowadzone wszystkie karty
    public Cards_Stack(){
        creatures = new ArrayList<>();
        creatures.add(new Creature(2, 3, 1));
        creatures.add(new Creature(9, 7, 4));
        creatures.add(new Creature(10, 6, 6));
        creatures.add(new Creature(5, 3, 4));
        creatures.add(new Creature(6, 6, 2));
        creatures.add(new Creature(1, 2, 2));
        /*
        TU BĘDZIE DUUUUŻO STWORÓW W PRZYSZŁOŚCI
         */
    }

    //poniższa metoda losuje kartę z tego "stosu" i ją zwraca
    //ma służyć do dobierania kart przez graczy
    public Creature giveCard(){
        Random rand = new Random();
        int n = rand.nextInt(creatures.size());
        Creature card = creatures.get(n);
        creatures.remove(n);
        return card;
    }

    //UWAGI:
    //- można się zastanowić co ma się stać w przypadku wyczerpania się kart
}
