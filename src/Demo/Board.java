package Demo;

import Creatures.A_Creature;
import Creatures.Creature;
import Rage_Cards.R_Card;

import java.util.ArrayList;
import java.util.List;

import static java.lang.System.exit;

public class Board {

    //dwie równoległe linie, po jednej dla każdego gracza, po nich będą poruszać się stwory
    private final List<Field> line1;
    private final List<Field> line2;

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
    }


    //ruch stworow pojedynczego gracza
    public void move(Player you, Player opponent, Discardeds_Stack discarded, Cards_Stack cards,  Rage_Cards rage_cards, Money money){

        //pomocniczy stwór z mocą A
        A_Creature space = null;
        int flag = 0;

        //gdy tura pierwszego
        if(you.id == 1){
            //stwor na ostatnim polu wchodzi do bazy przeciwnika
            if(!line1.get(4).empty){
                //jeśli miał moc A
                if(line1.get(4).creature.getPower().equals("A")){
                    space = (A_Creature) line1.get(4).creature;
                    //traci ją i się odrodzi później
                    if(space.getChance() == 1){
                        space.setChance(0);
                        line1.get(4).removeCard();
                        flag = 1;
                    }
                    else {
                        discarded.putCard(line1.get(4).removeCard());
                        you.counter--;
                    }
                }
                else {
                    discarded.putCard(line1.get(4).removeCard());
                    you.counter--;
                }

                //przeciwnik traci tarcze i jeśli wychodzi na minus, to przegrywa grę
                opponent.loseShield();
                if(opponent.showShields() == -1){
                    you.score += 2.0;
                    opponent.score -= 0.5;
                    endGame(you, opponent);
                }
                //w przeciwnym razie zdobywa kartę Rage
                else{
                    you.score += 1.0;
                    opponent.score -= 0.5;
                    R_Card rage_card = rage_cards.giveCard();
                    opponent.rage.putCard(rage_card);
                    System.out.println("GRACZ " + opponent.id + " otrzymał kartę *" + rage_card + "*");
                    rage_card.effect(opponent, you, this, discarded, cards, money, rage_cards);
                }
            }
            //następnie pozostałe stwory się przemieszczają i atakują stwory przeciwnika
            for(int i = 3; i >= 0; i--){
                if(!line1.get(i).empty){
                    line1.get(i +1 ).putCard(line1.get(i).removeCard());
                    if(!line2.get(i + 1).empty) {
                        if(fight(line1.get(i + 1), line2.get(i + 1), you, opponent, discarded, you.Crusher))
                            opponent.counter--;
                    }
                }
            }

            if(flag == 1){
                put(space, you, opponent, discarded);
            }
        }
        //gdy tura drugiego; różnica jest taka, że stwory gracza 2 idą w drugą stronę
        else {
            if(!line2.get(0).empty){
                //jeśli miał moc A
                if(line2.get(0).creature.getPower().equals("A")){
                    space = (A_Creature) line2.get(0).creature;
                    //traci ją i się odrodzi później
                    if(space.getChance() == 1){
                        space.setChance(0);
                        line2.get(0).removeCard();
                        flag = 1;
                    }
                    else {
                        discarded.putCard(line2.get(0).removeCard());
                        you.counter--;
                    }
                }
                else {
                    discarded.putCard(line2.get(0).removeCard());
                    you.counter--;
                }

                opponent.loseShield();
                if(opponent.showShields() == -1){
                    you.score += 2.0;
                    opponent.score -= 0.5;
                    endGame(you, opponent);
                }
                else{
                    you.score += 1.0;
                    opponent.score -= 0.5;
                    R_Card rage_card = rage_cards.giveCard();
                    opponent.rage.putCard(rage_card);
                    System.out.println("GRACZ " + opponent.id + " otrzymał kartę *" + rage_card + "*");
                    rage_card.effect(opponent, you, this, discarded, cards, money, rage_cards);
                }
            }
            for(int i = 1; i <= 4; i++){
                if(!line2.get(i).empty){
                    line2.get(i - 1).putCard(line2.get(i).removeCard());
                    if(!line1.get(i - 1).empty) {
                        if(fight(line2.get(i - 1), line1.get(i - 1), you, opponent, discarded, you.Crusher))
                            opponent.counter--;
                    }
                }
            }

            if(flag == 1){
                put(space, you, opponent, discarded);
            }
        }
    }

    //rozpatruje, czy broniąca się jednostka zginie, czy nie
    public boolean fight(Field attack, Field defense, Player you, Player opponent, Discardeds_Stack discardeds, int is_crusher){
        //jeśli właściciel atakującej jednostki posiada kartę Rage "Crusher", wtedy jego atak jest bardziej zabójczy
        if((is_crusher == 1 && attack.creature.getAttack() == defense.creature.getHp()) || attack.creature.getAttack() > defense.creature.getHp()){
            //jeżeli broniąca się postać ma moc A
            if(defense.creature.getPower().equals("A"))
            {
                A_Creature A = (A_Creature) defense.creature;
                //i jeszcze jej nie użyła, to zostaje zużyta, a sama postać jest ponownie wystawiona
                if(A.getChance() == 1){
                    A.setChance(0);
                    this.put(defense.creature, opponent, you, discardeds);
                    defense.removeCard();
                    return false;
                }
            }
            discardeds.putCard(defense.removeCard());
            return true;
        }
        return false;
    }


    //metoda umieszcza wybranego stwora na pierwszym polu linii odpowiedniego gracza
    //jednak najpierw sprawdza czy pole i jeśli potrzeba, kolejne pola, czy są zajęte, bo
    //jeżeli są, to przepycha najpierw stwory do przodu, one mają możliwość ataku
    //dopiero wtedy wybrany stwor staje na pierwszym polu i też wykonuje atak, jeśli ma kogo atakować
    public void put(Creature creature, Player you, Player opponent,  Discardeds_Stack discardeds){
        if(you.id == 1){
            if(!line1.get(0).empty){
                if(!line1.get(1).empty){
                    if(!line1.get(2).empty){
                        line1.get(3).putCard(line1.get(2).removeCard());
                        if(!line2.get(3).empty) {
                            if(fight(line1.get(3), line2.get(3), you, opponent, discardeds, you.Crusher))
                                opponent.counter--;
                        }
                    }
                    line1.get(2).putCard(line1.get(1).removeCard());
                    if(!line2.get(2).empty) {
                        if(fight(line1.get(2), line2.get(2), you, opponent, discardeds, you.Crusher))
                            opponent.counter--;
                    }
                }
                line1.get(1).putCard(line1.get(0).removeCard());
                if(!line2.get(1).empty) {
                    if(fight(line1.get(1), line2.get(1), you, opponent, discardeds, you.Crusher))
                        opponent.counter--;
                }
            }
            line1.get(0).putCard(creature);
            if(!line2.get(0).empty){
                if(fight(line1.get(0), line2.get(0), you, opponent, discardeds, you.Crusher))
                    opponent.counter--;
            }
        }

        else{
            if(!line2.get(4).empty){
                if(!line2.get(3).empty){
                    if(!line2.get(2).empty){
                        line2.get(1).putCard(line2.get(2).removeCard());
                        if(!line1.get(1).empty) {
                            if(fight(line2.get(1), line1.get(1), you, opponent, discardeds, you.Crusher))
                                opponent.counter--;
                        }
                    }
                    line2.get(2).putCard(line2.get(3).removeCard());
                    if(!line1.get(2).empty) {
                        if(fight(line2.get(2), line1.get(2), you, opponent, discardeds, you.Crusher))
                            opponent.counter--;
                    }
                }
                line2.get(3).putCard(line2.get(4).removeCard());
                if(!line1.get(3).empty) {
                    if(fight(line2.get(3), line1.get(3), you, opponent, discardeds, you.Crusher))
                        opponent.counter--;
                }
            }
            line2.get(4).putCard(creature);
            if(!line1.get(4).empty){
                if(fight(line2.get(4), line1.get(4), you, opponent, discardeds, you.Crusher))
                    opponent.counter--;
            }
        }
    }

    //wstawia wybranego stwora na pole o podanym indexie należące do danego gracza, bez wykonania ataku
    public void insertCard(Creature creature, int index, int player_id){
        if(player_id == 1){
            line1.get(index).putCard(creature);
        }
        else{
            line2.get(index).putCard(creature);
        }
    }

    //zdejmuje wybraną kartę z linii podanego gracza
    public Creature removeCard(int player_id, int index){
        if(player_id == 1){
            return line1.get(index).removeCard();
        }
        else {
            return line2.get(index).removeCard();
        }
    }

    //zakończenie gry
    public void endGame(Player you, Player opponent){
        System.out.println("\n" + "\n" + "\n" + "\n" + "\n"  + "\n" + "\n" + "\n" + "\n" + "\n");
        System.out.println("GRA SKOŃCZONA!");
        System.out.println("Gracz 1 " + you.score + ":" + opponent.score + " Gracz 2");
        exit(0);
    }


    //sprawdza czy pole o podanym indexie na linii podanego gracza jest puste
    public boolean empty(int player_id, int index){
        if(player_id == 1){
            return line1.get(index).empty;
        }
        else{
            return line2.get(index).empty;
        }
    }

    //plansza składa się odpowiednio z linii 2, przerwy i linii 1
    @Override
    public String toString(){
        String plansza = "";
        for(int i = 0; i < 5; i++){
            if(line2.get(i).empty)
                plansza += " <--- ";
            else
                plansza += line2.get(i).creature.onBoard();

            if(i < 4)
                plansza += " | ";
            else
                plansza += "\n";
        }

        for(int i = 0; i < 50; i++){
            plansza += "-";
        }
        plansza += "\n";

        for(int i = 0; i < 5; i++){
            if(line1.get(i).empty)
                plansza += " ---> ";
            else
                plansza += line1.get(i).creature.onBoard();

            if(i < 4)
                plansza += " | ";
            else
                plansza += "\n";
        }
        return plansza;
    }


    //pojedyncze pole
    private static class Field{
        public boolean empty;
        public Creature creature;

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
