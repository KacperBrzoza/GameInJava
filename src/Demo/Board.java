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
    public void move(Player p1, Player p2, Discardeds_Stack discarded, Cards_Stack cards,  Rage_Cards rage_cards, Money money){
        //gdy tura pierwszego
        if(p1.id == 1){
            //stwor na ostatnim polu wchodzi do bazy przeciwnika
            if(!line1.get(4).empty){
                discarded.putCard(line1.get(4).removeCard());
                p1.counter--;

                //przeciwnik traci tarcze i jeśli wychodzi na minus, to przegrywa grę
                p2.loseShield();
                if(p2.showShields() == -1){
                    p1.score += 2.0;
                    p2.score -= 0.5;
                    endGame(p1, p2);
                }
                //w przeciwnym razie zdobywa kartę Rage
                else{
                    p1.score += 1.0;
                    p2.score -= 0.5;
                    R_Card rage_card = rage_cards.giveCard();
                    p2.rage.putCard(rage_card);
                    System.out.println("GRACZ " + p2.id + " otrzymał kartę *" + rage_card + "*");
                    rage_card.effect(p2, p1, this, discarded, cards, money);
                }
            }
            //następnie pozostałe stwory się przemieszczają i atakują stwory przeciwnika
            for(int i = 3; i >= 0; i--){
                if(!line1.get(i).empty){
                    line1.get(i +1 ).putCard(line1.get(i).removeCard());
                    if(!line2.get(i + 1).empty) {
                        if(fight(line1.get(i + 1), line2.get(i + 1), discarded))
                            p2.counter--;
                    }
                }
            }
        }
        //gdy tura drugiego; różnica jest taka, że stwory gracza 2 idą w drugą stronę
        else {
            if(!line2.get(0).empty){
                discarded.putCard(line2.get(0).removeCard());
                p1.counter--;

                p2.loseShield();
                if(p2.showShields() == -1){
                    p1.score += 2.0;
                    p2.score -= 0.5;
                    endGame(p1, p2);
                }
                else{
                    p1.score += 1.0;
                    p2.score -= 0.5;
                    R_Card rage_card = rage_cards.giveCard();
                    p2.rage.putCard(rage_card);
                    System.out.println("GRACZ " + p2.id + " otrzymał kartę *" + rage_card + "*");
                    rage_card.effect(p1, p2, this, discarded, cards, money);
                }
            }
            for(int i = 1; i <= 4; i++){
                if(!line2.get(i).empty){
                    line2.get(i - 1).putCard(line2.get(i).removeCard());
                    if(!line1.get(i - 1).empty) {
                        if(fight(line2.get(i - 1), line1.get(i - 1), discarded))
                            p2.counter--;
                    }
                }
            }
        }
    }

    //rozpatruje, czy broniąca się jednostka zginie, czy nie
    public boolean fight(Field attack, Field defense, Discardeds_Stack discardeds){
        if(attack.creature.getAttack() > defense.creature.getHp()){
            discardeds.putCard(defense.removeCard());
            return true;
        }
        return false;
    }


    /*
    !!! UWAGA !!!
    PRAWDOPODONIE METODA put WYAMAGA PRZEBUDOWY
     */

    //metoda umieszcza wybranego stwora na pierwszym polu linii odpowiedniego gracza
    //jednak najpierw sprawdza czy pole i jeśli potrzeba, kolejne pola, czy są zajęte, bo
    //jeżeli są, to przepycha najpierw stwory do przodu, one mają możliwość ataku
    //dopiero wtedy wybrany stwor staje na pierwszym polu i też wykonuje atak, jeśli ma kogo atakować
    public void put(Creature creature, Player p1, Player p2,  Discardeds_Stack discardeds){
        if(p1.id == 1){
            if(!line1.get(0).empty){
                if(!line1.get(1).empty){
                    if(!line1.get(2).empty){
                        line1.get(3).putCard(line1.get(2).removeCard());
                        if(!line2.get(3).empty) {
                            if(fight(line1.get(3), line2.get(3), discardeds))
                                p2.counter--;
                        }
                    }
                    line1.get(2).putCard(line1.get(1).removeCard());
                    if(!line2.get(2).empty) {
                        if(fight(line1.get(2), line2.get(2), discardeds))
                            p2.counter--;
                    }
                }
                line1.get(1).putCard(line1.get(0).removeCard());
                if(!line2.get(1).empty) {
                    if(fight(line1.get(1), line2.get(1), discardeds))
                        p2.counter--;
                }
            }
            line1.get(0).putCard(creature);
            if(!line2.get(0).empty){
                if(fight(line1.get(0), line2.get(0), discardeds))
                    p2.counter--;
            }
        }

        else{
            if(!line2.get(4).empty){
                if(!line2.get(3).empty){
                    if(!line2.get(2).empty){
                        line2.get(1).putCard(line2.get(2).removeCard());
                        if(!line1.get(1).empty) {
                            if(fight(line2.get(1), line1.get(1), discardeds))
                                p1.counter--;
                        }
                    }
                    line2.get(2).putCard(line1.get(3).removeCard());
                    if(!line1.get(2).empty) {
                        if(fight(line2.get(2), line1.get(2), discardeds))
                            p1.counter--;
                    }
                }
                line2.get(3).putCard(line2.get(4).removeCard());
                if(!line1.get(3).empty) {
                    if(fight(line2.get(3), line1.get(3), discardeds))
                        p1.counter--;
                }
            }
            line2.get(4).putCard(creature);
            if(!line1.get(4).empty){
                if(fight(line2.get(4), line1.get(4), discardeds))
                    p1.counter--;
            }
        }
    }

    //funkcja zakańczająca grę
    public void endGame(Player p1, Player p2){
        System.out.println("\n" + "\n" + "\n" + "\n" + "\n"  + "\n" + "\n" + "\n" + "\n" + "\n");
        System.out.println("GRA SKOŃCZONA!");
        System.out.println("Gracz 1 " + p1.score + ":" + p2.score + " Gracz 2");
        exit(0);
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
