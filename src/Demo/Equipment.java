package Demo;

import Creatures.Creature;

import java.util.ArrayList;
import java.util.List;

public class Equipment {

    private List<Field> fields;

    Equipment(){
        fields = new ArrayList<Field>();
    }

    //add creature

    //remove creature

    //println fields

}

class Field{
    Creature creature;
    int quantity;

    Field(Creature creature){
        this.creature = creature;
        quantity = 1;
    }
}
