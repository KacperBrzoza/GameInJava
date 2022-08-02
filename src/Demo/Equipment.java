package Demo;

import Creatures.Creature;

import java.util.ArrayList;
import java.util.List;

public class Equipment {

    private final List<Field> fields;

    Equipment(){
        fields = new ArrayList<>();
    }

    //metoda sprawdza czy stwór będący argumentem jest już w ekwipunku
    //jeśli tak, to jedynie zwiększa licznik pola, na którym się znajduje
    //w przeciwnym wypadku tworzy nowe pole dla tego stwora
    public void addCreature(Creature creature){
        for(int i = 0; i < fields.size(); i++){
            if(fields.get(i).creature.equals(creature)){
                fields.get(i).quantity++;
            }
            if(i+1 == fields.size())
                fields.add(new Field(creature));
        }
    }

    //zwraca wybraną postać i usuwa ją z ekwipunku
    //jeśli takiej samej karty było w ekwipunku więcej niż 1, to zmniejsza ilość posiadanej karty
    //w przeciwnym wypadku czyści ekwipunek z pola po wybranej karcie
    public Creature pickCreature(int index){
        Creature creature = fields.get(index).creature;
        if(fields.get(index).quantity > 1)
            fields.get(index).quantity--;
        else
            fields.remove(index);
        return creature;
    }

    //funkcja tymczasowa na potrzeby testów
    @Override
    public String toString(){
        String stan_eq = "";
        for (int i = 0; i < fields.size(); i++){
            stan_eq += fields.get(i).creature;
            if(fields.get(i).quantity > 1)
                stan_eq += " | ILOSC: " + fields.get(i).quantity;
            stan_eq += "\n";
        }
        return stan_eq;
    }

    //pomocnicza klasa pojedynczego pola, które zawiera stwora i jego ilość
    private static class Field{
        Creature creature;
        int quantity;

        Field(Creature creature){
            this.creature = creature;
            quantity = 1;
        }
    }
}


