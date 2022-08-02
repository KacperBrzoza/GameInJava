package Demo;

import Creatures.Creature;

import java.util.ArrayList;
import java.util.List;

public class Board {

    //dwie równoległe linie, po jednej dla każdego gracza, po nich będą poruszać się stwory
    private List<Field> line1;
    private List<Field> line2;
    //po jednym liczniku do każdej linii; służą do ograniczenia wystawianych jednostek do maks 4
    private int counter1;
    private int counter2;

    public Board(){
        line1 = new ArrayList<>();
        line2 = new ArrayList<>();

        //każda linia ma po 5 pól
        for (int i = 0; i < 5; i++){
            line1.add(new Field());
        }
        for (int i = 0; i < 5; i++){
            line2.add(new Field());
        }

        counter1 = 0;
        counter2 = 0;
    }


    //ruch stworow pojedynczego gracza
    public void move(int player_id, Discardeds_Stack discardeds){
        //gdy jest tura pierwszego
        if(player_id == 1){

            //jeżeli na ostatnim polu linii pierwszego gracza jest stwór, to...
            if(!line1.get(4).empty){
                
                ///...trafia on na stos kart odrzuconych
                discardeds.putCard(line1.get(4).removeCard());
            }
        }
        //gdy tura drugiego
        else {

        }
    }


    private static class Field{
        boolean empty;
        Creature creature;

        public Field(){
            empty = true;
            creature = null;
        }

        //stwór podany w argumencie będzie umieszczony na tym polu
        public void putCard(Creature creature){
            this.creature = creature;
            empty = false;
        }

        //metoda pozwalająca zabrać stwora z tego pola
        public Creature removeCard(){
            empty = true;
            Creature creature = this.creature;
            this.creature = null;
            return creature;
        }
    }
}
