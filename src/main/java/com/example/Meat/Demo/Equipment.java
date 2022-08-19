package com.example.Meat.Demo;

import com.example.Meat.Creatures.*;
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

        //jednostka z mocą X trafiając do ekwipunku ma odświeżaną moc
        if(creature.getPower().equals("X")){
            X_Creature x = (X_Creature) creature;
            x.setUse(1);
        }

        //jednostka z mocą G trafiając do ekwipunku ma odświeżaną moc
        if(creature.getPower().equals("G")){
            G_Creature g = (G_Creature) creature;
            g.setUse(1);
        }

        //zdejmowanie zatrucia (działania mocy J)
        if(creature.ifPoisoned())
            creature.setPoisoned(0);

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

    //zwraca stwora z pola w ekwipunku o zadanym indexie
    public Creature copy(int index){return fields.get(index).creature;}

    //zwraca koszt stwora na podanej pozycji w ekwipunku
    public int checkCost(int index){
        return fields.get(index).creature.getCost();
    }

    //zwraca informację, czy stwór na podanej pozycji w ekwipunku ma moc E
    public boolean ifE(int index){
        return fields.get(index).creature.getPower().equals("E");
    }

    //wielkosc ekwipunku
    public int size(){
        return fields.size();
    }



    //funkcja tymczasowa na potrzeby testów
    @Override
    public String toString(){
        StringBuilder stan_eq = new StringBuilder();
        for (int i = 0; i < fields.size(); i++){
            stan_eq.append("(").append(i).append(") ");
            stan_eq.append(fields.get(i).creature);
            if(fields.get(i).quantity > 1)
                stan_eq.append(" | ILOSC: ").append(fields.get(i).quantity);
            if(i+1 != fields.size())
                stan_eq.append("\n");
        }
        return stan_eq.toString();
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


