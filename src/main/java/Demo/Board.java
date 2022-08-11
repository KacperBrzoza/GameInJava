package Demo;

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
        //gdy tura pierwszego
        if(you.id == 1){
            //stwor na ostatnim polu wchodzi do bazy przeciwnika
            if(!line1.get(4).empty){
                discarded.putCard(line1.get(4).removeCard());
                you.counter--;

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
                    System.out.println("GRACZ " + opponent.id + " otrzymal karte *" + rage_card + "*");
                    rage_card.effect(opponent, you, this, discarded, cards, money, rage_cards);
                    //jeżeli zdobytą kartą była karta Rage "Swarm"
                    if(opponent.Swarm == 1){
                        for (int i = 0; i < 5; i++){
                            if(!line2.get(i).empty) {
                                if (line2.get(i).creature.getAttack() == 2) {
                                    line2.get(i).creature.setSwarm(1);
                                    line2.get(i).creature.increaseAttack();
                                }
                            }
                        }
                    }
                    //jeżeli zdobytą kartą była karta Rage "Unbroaken"
                    if(opponent.Unbroaken == 1){
                        for (int i = 0; i < 5; i++){
                            if(!line2.get(i).empty) {
                                if (line2.get(i).creature.getHp() == 2) {
                                    line2.get(i).creature.setUnbroaken(1);
                                    line2.get(i).creature.increaseHp();
                                }
                            }
                        }
                    }
                }
            }
            //następnie pozostałe stwory się przemieszczają i atakują stwory przeciwnika
            for(int i = 3; i >= 0; i--){
                if(!line1.get(i).empty){
                    line1.get(i +1 ).putCard(line1.get(i).removeCard());
                    if(!line2.get(i + 1).empty) {
                        if(fight(line1.get(i + 1), line2.get(i + 1), discarded, you.Crusher))
                            opponent.counter--;
                    }
                }
            }
        }
        //gdy tura drugiego; różnica jest taka, że stwory gracza 2 idą w drugą stronę
        else {
            if(!line2.get(0).empty){
                discarded.putCard(line2.get(0).removeCard());
                you.counter--;

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
                    System.out.println("GRACZ " + opponent.id + " otrzymal karte *" + rage_card + "*");
                    rage_card.effect(opponent, you, this, discarded, cards, money, rage_cards);
                    //jeżeli zdobytą kartą była karta Rage "Swarm"
                    if(opponent.Swarm == 1){
                        for (int i = 0; i < 5; i++){
                            if(!line1.get(i).empty) {
                                if (line1.get(i).creature.getAttack() == 2) {
                                    line1.get(i).creature.setSwarm(1);
                                    line1.get(i).creature.increaseAttack();
                                }
                            }
                        }
                    }
                    //jeżeli zdobytą kartą była karta Rage "Unbroaken"
                    if(opponent.Unbroaken == 1){
                        for (int i = 0; i < 5; i++){
                            if(!line2.get(i).empty) {
                                if (line1.get(i).creature.getHp() == 2) {
                                    line1.get(i).creature.setUnbroaken(1);
                                    line1.get(i).creature.increaseHp();
                                }
                            }
                        }
                    }
                }
            }
            for(int i = 1; i <= 4; i++){
                if(!line2.get(i).empty){
                    line2.get(i - 1).putCard(line2.get(i).removeCard());
                    if(!line1.get(i - 1).empty) {
                        if(fight(line2.get(i - 1), line1.get(i - 1), discarded, you.Crusher))
                            opponent.counter--;
                    }
                }
            }
        }
    }

    //rozpatruje, czy broniąca się jednostka zginie, czy nie
    public boolean fight(Field attack, Field defense, Discardeds_Stack discardeds, int is_crusher){
        //jeśli właściciel atakującej jednostki posiada kartę Rage "Crusher", wtedy jego atak jest bardziej zabójczy
        if((is_crusher == 1 && attack.creature.getAttack() == defense.creature.getHp()) || attack.creature.getAttack() > defense.creature.getHp()){
            discardeds.putCard(defense.removeCard());
            return true;
        }
        return false;
    }

    //metoda rozszerzająca funkcję fight o argument wybierający konkretne pola do rozpatrzenia walki
    public void fight(Player you, Player opponent, int target, Discardeds_Stack discardeds){
        if(you.id == 1){
            if(fight(line1.get(target), line2.get(target), discardeds, you.Crusher))
                opponent.counter--;
        }
        else{
            if(fight(line2.get(target), line1.get(target), discardeds, you.Crusher))
                opponent.counter--;
        }
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
                            if(fight(line1.get(3), line2.get(3), discardeds, you.Crusher))
                                opponent.counter--;
                        }
                    }
                    line1.get(2).putCard(line1.get(1).removeCard());
                    if(!line2.get(2).empty) {
                        if(fight(line1.get(2), line2.get(2), discardeds, you.Crusher))
                            opponent.counter--;
                    }
                }
                line1.get(1).putCard(line1.get(0).removeCard());
                if(!line2.get(1).empty) {
                    if(fight(line1.get(1), line2.get(1), discardeds, you.Crusher))
                        opponent.counter--;
                }
            }
            line1.get(0).putCard(creature);
            if(!line2.get(0).empty){
                if(fight(line1.get(0), line2.get(0), discardeds, you.Crusher))
                    opponent.counter--;
            }
        }

        else{
            if(!line2.get(4).empty){
                if(!line2.get(3).empty){
                    if(!line2.get(2).empty){
                        line2.get(1).putCard(line2.get(2).removeCard());
                        if(!line1.get(1).empty) {
                            if(fight(line2.get(1), line1.get(1), discardeds, you.Crusher))
                                opponent.counter--;
                        }
                    }
                    line2.get(2).putCard(line2.get(3).removeCard());
                    if(!line1.get(2).empty) {
                        if(fight(line2.get(2), line1.get(2), discardeds, you.Crusher))
                            opponent.counter--;
                    }
                }
                line2.get(3).putCard(line2.get(4).removeCard());
                if(!line1.get(3).empty) {
                    if(fight(line2.get(3), line1.get(3), discardeds, you.Crusher))
                        opponent.counter--;
                }
            }
            line2.get(4).putCard(creature);
            if(!line1.get(4).empty){
                if(fight(line2.get(4), line1.get(4), discardeds, you.Crusher))
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

    //podmienia kartę gracza na podanym polu
    public void setCreature(int player_id, int index, Creature creature){
        if(player_id == 1){
            line1.get(index).setCard(creature);
        }
        else{
            line2.get(index).setCard(creature);
        }
    }

    //zwraca stwora jakiego posiada dany gracz na danym polu
    public Creature getCreature(int player_id, int index){
        if(player_id == 1){
            return line1.get(index).creature;
        }
        else{
            return line2.get(index).creature;
        }
    }

    //zakończenie gry
    public void endGame(Player you, Player opponent){
        System.out.println("\n" + "\n" + "\n" + "\n" + "\n"  + "\n" + "\n" + "\n" + "\n" + "\n");
        System.out.println("GRA SKONCZONA!");
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
                plansza += " <-- ";
            else
                plansza += line2.get(i).creature.onBoard();

            if(i < 4)
                plansza += " | ";
            else
                plansza += "\n";
        }

        for(int i = 0; i < 37; i++){
            plansza += "-";
        }
        plansza += "\n";

        for(int i = 0; i < 5; i++){
            if(line1.get(i).empty)
                plansza += " --> ";
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

        public void setCard(Creature creature){this.creature = creature;}
        public Creature getCard(){return this.creature;}
    }
}
