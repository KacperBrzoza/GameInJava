package Demo;

import Creatures.A_Creature;
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

        //jeżeli stwór posiada moc A, wtedy pole "szansa" jest przestawiane na 1
        if(creature.getPower().equals("A")){
            A_Creature A = (A_Creature) creature;
            A.setChance(1);
            creature = A;
        }

        if(fields.size() == 0){
            fields.add(new Field(creature));
        }
        else {
            for (int i = 0; i < fields.size(); i++) {
                if (fields.get(i).creature.theSame(creature)) {
                    fields.get(i).quantity++;
                    break;
                }
                if (i + 1 == fields.size()) {
                    fields.add(new Field(creature));
                    break;
                }
            }
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


    //zwraca koszt stwora na podanej pozycji w ekwipunku
    public int checkCost(int index){
        return fields.get(index).creature.getCost();
    }


    //wielkosc ekwipunku
    public int size(){
        return fields.size();
    }


    //funkcja tymczasowa na potrzeby testów
    @Override
    public String toString(){
        String stan_eq = "";
        for (int i = 0; i < fields.size(); i++){
            stan_eq += "(" + i + ") ";
            stan_eq += fields.get(i).creature;
            if(fields.get(i).quantity > 1)
                stan_eq += " | ILOSC: " + fields.get(i).quantity;
            if(i+1 != fields.size())
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


