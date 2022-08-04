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
        creatures.add(new Creature(0, 4, 2));
        creatures.add(new Creature(0, 2, 3));
        for(int i = 0; i < 3; i++)
            creatures.add(new Creature(1, 2, 2));
        creatures.add(new Creature(2, 2, 2));
        creatures.add(new Creature(2, 1, 3));
        creatures.add(new Creature(2, 3, 1));
        creatures.add(new Creature(3, 4, 1));
        for(int i = 0; i < 5; i++)
            creatures.add(new Creature(3, 3, 2));
        for(int i = 0; i < 3; i++)
            creatures.add(new Creature(4, 2, 4));
        creatures.add(new Creature(4, 5, 1));
        for(int i = 0; i < 3; i++)
            creatures.add(new Creature(4, 4, 2));
        for(int i = 0; i < 3; i++)
            creatures.add(new Creature(4, 3, 3));
        creatures.add(new Creature(5, 5, 2));
        for(int i = 0; i < 3; i++)
            creatures.add(new Creature(5, 4, 3));
        for(int i = 0; i < 3; i++)
            creatures.add(new Creature(5, 3, 4));
        creatures.add(new Creature(6, 5, 3));
        creatures.add(new Creature(6, 3, 5));
        creatures.add(new Creature(6, 4, 4));
        creatures.add(new Creature(6, 6, 2));
        creatures.add(new Creature(7, 4, 5));
        creatures.add(new Creature(7, 1, 8));
        creatures.add(new Creature(7, 6, 3));
        creatures.add(new Creature(7, 3, 6));
        creatures.add(new Creature(7, 5, 4));
        creatures.add(new Creature(8, 5, 5));
        creatures.add(new Creature(8, 8, 2));
        creatures.add(new Creature(8, 3, 7));
        creatures.add(new Creature(8, 7, 3));
        creatures.add(new Creature(8, 4, 6));
        creatures.add(new Creature(8, 6, 4));
        creatures.add(new Creature(9, 5, 6));
        creatures.add(new Creature(9, 9, 2));
        creatures.add(new Creature(9, 6, 5));
        creatures.add(new Creature(9, 8, 3));
        creatures.add(new Creature(9, 4, 7));
        creatures.add(new Creature(9, 7, 4));
        creatures.add(new Creature(10, 5, 7));
        creatures.add(new Creature(10, 7, 5));
        creatures.add(new Creature(10, 4, 8));
        creatures.add(new Creature(10, 8, 4));
        creatures.add(new Creature(10, 6, 6));
        creatures.add(new Creature(11, 7, 6));
        creatures.add(new Creature(11, 6, 7));
        creatures.add(new Creature(12, 7, 7));

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
    // Czy koniec gry?
    //Preferencja osobista (Zalezy czy bysmy chcieli,zeby gracze grali bardzo dlugo, czy chcemy zrownowazone gry - Tak jak w Hearthstone
    //odbieranie coraz wiecej zycia po wyczerpaniu stosu kart
    // Czy ponowna inicjalizacja całego "stosu" zakrytych kart?
}
