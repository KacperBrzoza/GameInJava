package com.example.Meat.Demo;

import com.example.Main.Game.GameController;
import com.example.Meat.Creatures.*;
import com.example.Meat.Rage_Cards.R_Card;
import javafx.scene.image.Image;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.exit;

public class Board {

    //dwie równoległe linie, po jednej dla każdego gracza, po nich będą poruszać się stwory
    private final List<Field> line1;
    private final List<Field> line2;

    //awaryjne pola do przechwycenia wyniku w razie rozłącznia, któregoś z graczy
    public float  PLAYER_ONE_POINTS;
    public float  PLAYER_TWO_POINTS;

    public Board(float PLAYER_ONE_POINTS, float PLAYER_TWO_POINTS){
        line1 = new ArrayList<>();
        line2 = new ArrayList<>();

        //każda linia ma po 5 pól
        for (int i = 0; i < 5; i++){
            line1.add(new Field(i));
        }
        for (int i = 0; i < 5; i++){
            line2.add(new Field(i));
        }

        this.PLAYER_ONE_POINTS = PLAYER_ONE_POINTS;
        this.PLAYER_TWO_POINTS = PLAYER_TWO_POINTS;
    }


    //ruch stworow pojedynczego gracza
    public void move(Player you, Player opponent, Discardeds_Stack discarded, Cards_Stack cards, Rage_Cards rage_cards, Money money, BufferedWriter out, BufferedReader in, GameController gameController) throws IOException {
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
                    PLAYER_ONE_POINTS += 2.0;
                    opponent.score -= 0.5;
                    PLAYER_TWO_POINTS -= 0.5;
                    endGame(opponent, you, out);
                }
                //w przeciwnym razie zdobywa kartę Rage
                else{
                    you.score += 1.0;
                    PLAYER_ONE_POINTS += 1.0;
                    opponent.score -= 0.5;
                    PLAYER_TWO_POINTS -= 0.5;
                    R_Card rage_card = rage_cards.giveCard();
                    opponent.rage.putCard(rage_card);
                    System.out.println("GRACZ " + opponent.id + " otrzymal karte *" + rage_card + "*");
                    //out.println("GRACZ " + opponent.id + " otrzymal karte *" + rage_card + "*");
                    rage_card.effect(opponent, you, this, discarded, cards, money, rage_cards, out, in, gameController);
                    //jeżeli zdobytą kartą była karta Rage "Swarm" zwieksza atak o 1 wszystkim swoim wystawionym jednostkom z atakiem = 2
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
                    //jeżeli zdobytą kartą była karta Rage "Unbroaken" zwieksza obronę o 1 wszystkim swoim wystawionym jednostkom z obroną = 2
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
                    line1.get(i + 1).putCard(line1.get(i).removeCard());
                    //normalna walka
                    if (!line2.get(i + 1).empty) {
                        if (fight(line1.get(i + 1), line2.get(i + 1), discarded, you, opponent))
                            opponent.counter--;
                    }
                    //dodatkowa walka dla stworów z mocą F
                    else{
                        if(i + 2 < 5) {
                            if (line1.get(i + 1).creature.getPower().equals("F")) {
                                if (!line2.get(i + 2).empty) {
                                    if (fight(line1.get(i + 1), line2.get(i + 2), discarded, you, opponent))
                                        opponent.counter--;
                                }
                            }
                        }
                    }
                }
            }
        }
        //gdy tura drugiego; różnica jest taka, że stwory gracza 2 idą w drugą stronę
        else {
            //stwor na ostatnim polu wchodzi do bazy przeciwnika
            if(!line2.get(0).empty){
                discarded.putCard(line2.get(0).removeCard());
                you.counter--;

                //przeciwnik traci tarcze i jeśli wychodzi na minus, to przegrywa grę
                opponent.loseShield();
                if(opponent.showShields() == -1){
                    you.score += 2.0;
                    opponent.score -= 0.5;
                    endGame(you, opponent, out);
                }
                //w przeciwnym razie zdobywa kartę Rage
                else{
                    you.score += 1.0;
                    opponent.score -= 0.5;
                    R_Card rage_card = rage_cards.giveCard();
                    opponent.rage.putCard(rage_card);
                    System.out.println("GRACZ " + opponent.id + " otrzymal karte *" + rage_card + "*");
                    //out.println("GRACZ " + opponent.id + " otrzymal karte *" + rage_card + "*");
                    rage_card.effect(opponent, you, this, discarded, cards, money, rage_cards, out, in, gameController);
                    //jeżeli zdobytą kartą była karta Rage "Swarm" zwieksza atak o 1 wszystkim swoim wystawionym jednostkom z atakiem = 2
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
                    //jeżeli zdobytą kartą była karta Rage "Unbroaken" zwieksza obronę o 1 wszystkim swoim wystawionym jednostkom z obroną = 2
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
            //następnie pozostałe stwory się przemieszczają i atakują stwory przeciwnika
            for(int i = 1; i <= 4; i++){
                if(!line2.get(i).empty){
                    line2.get(i - 1).putCard(line2.get(i).removeCard());
                    //normalna walka
                    if (!line1.get(i - 1).empty) {
                        if (fight(line2.get(i - 1), line1.get(i - 1), discarded, you, opponent))
                            opponent.counter--;
                    }
                    //dodatkowa walka dla stworów z mocą F
                    else{
                        if(i - 2 >= 0) {
                            if (line2.get(i - 1).creature.getPower().equals("F")) {
                                if (!line1.get(i - 2).empty) {
                                    if (fight(line2.get(i - 1), line1.get(i - 2), discarded, you, opponent))
                                        opponent.counter--;
                                }
                            }
                        }
                    }
                }
            }
        }
        exportImagesFromBackend(gameController);
    }

    public void exportImagesFromBackend(GameController gameController){
        File file;
        String path;
        Image image0, image1, image2, image3, image4, image5, image6, image7, image8, image9;

        if(this.line1.get(0).empty) {
            image0 = null;
            GameController.server.sendMessageToClient("SET_FIELD_0_N");
        }
        else{
            path = this.line1.get(0).creature.path;
            file = new File(path);
            image0 = new Image(file.toURI().toString());
            GameController.server.sendMessageToClient("SET_FIELD_0_" + path);
        }

        if(this.line1.get(1).empty) {
            image1 = null;
            GameController.server.sendMessageToClient("SET_FIELD_1_N");
        }
        else {
            path = this.line1.get(1).creature.path;
            file = new File(path);
            image1 = new Image(file.toURI().toString());
            GameController.server.sendMessageToClient("SET_FIELD_1_" + path);
        }

        if(this.line1.get(2).empty) {
            image2 = null;
            GameController.server.sendMessageToClient("SET_FIELD_2_N");
        }
        else {
            path = this.line1.get(2).creature.path;
            file = new File(path);
            image2 = new Image(file.toURI().toString());
            GameController.server.sendMessageToClient("SET_FIELD_2_" + path);
        }

        if(this.line1.get(3).empty) {
            image3 = null;
            GameController.server.sendMessageToClient("SET_FIELD_3_N");
        }
        else {
            path = this.line1.get(3).creature.path;
            file = new File(path);
            image3 = new Image(file.toURI().toString());
            GameController.server.sendMessageToClient("SET_FIELD_3_" + path);
        }

        if(this.line1.get(4).empty) {
            image4 = null;
            GameController.server.sendMessageToClient("SET_FIELD_4_N");
        }
        else {
            path = this.line1.get(4).creature.path;
            file = new File(path);
            image4 = new Image(file.toURI().toString());
            GameController.server.sendMessageToClient("SET_FIELD_4_" + path);
        }

        if(this.line2.get(4).empty) {
            image5 = null;
            GameController.server.sendMessageToClient("SET_FIELD_5_N");
        }
        else {
            path = this.line2.get(4).creature.path;
            file = new File(path);
            image5 = new Image(file.toURI().toString());
            GameController.server.sendMessageToClient("SET_FIELD_5_" + path);
        }

        if(this.line2.get(3).empty) {
            image6 = null;
            GameController.server.sendMessageToClient("SET_FIELD_6_N");
        }
        else {
            path = this.line2.get(3).creature.path;
            file = new File(path);
            image6 = new Image(file.toURI().toString());
            GameController.server.sendMessageToClient("SET_FIELD_6_" + path);
        }

        if(this.line2.get(2).empty) {
            image7 = null;
            GameController.server.sendMessageToClient("SET_FIELD_7_N");
        }
        else {
            path = this.line2.get(2).creature.path;
            file = new File(path);
            image7 = new Image(file.toURI().toString());
            GameController.server.sendMessageToClient("SET_FIELD_7_" + path);
        }

        if(this.line2.get(1).empty) {
            image8 = null;
            GameController.server.sendMessageToClient("SET_FIELD_8_N");
        }
        else {
            path = this.line2.get(1).creature.path;
            file = new File(path);
            image8 = new Image(file.toURI().toString());
            GameController.server.sendMessageToClient("SET_FIELD_8_" + path);
        }

        if(this.line2.get(0).empty) {
            image9 = null;
            GameController.server.sendMessageToClient("SET_FIELD_9_N");
        }
        else {
            path = this.line2.get(0).creature.path;
            file = new File(path);
            image9 = new Image(file.toURI().toString());
            GameController.server.sendMessageToClient("SET_FIELD_9_" + path);
        }
        GameController.setBattleField(gameController.fields, image0, image1, image2, image3, image4, image5, image6, image7, image8, image9);
        GameController.showBattleField(gameController.fields, gameController.mygrid0, gameController.mygrid1, gameController.mygrid2, gameController.mygrid3, gameController.mygrid4, gameController.enemygrid0, gameController.enemygrid1, gameController.enemygrid2, gameController.enemygrid3, gameController.enemygrid4);
        GameController.server.sendMessageToClient("SHOW_BATTLE_FIELD");
    }

    //rozpatruje, czy broniąca się jednostka zginie, czy nie
    public boolean fight(Field attack, Field defense, Discardeds_Stack discardeds, Player you, Player opponent){

        //gdy postać ma moc X
        if(attack.creature.getPower().equals("X")){
            X_Creature x = (X_Creature) attack.creature;

            //i jeszcze jej nie użyła
            if(x.getUse() == 1){
                x.setUse(0);

                //próba ratowania się kartą z mocą U przez broniącego się
                if(defense.creature.getImmortal(opponent, discardeds, this, defense.position))
                    return false;

                //próba ratowania się kartą z mocą R przez broniącego się
                if(defense.creature.getHelp(opponent, discardeds, this, defense.position))
                    return false;

                //sprawdzenie czy karta broniąca ma moc N i jej jeszcze nie wykorzystała
                if(defense.creature.getPower().equals("N")) {
                    N_Creature n = (N_Creature) defense.creature;
                    if (n.getUse() == 1) {
                        n.setUse(0);
                        return false;
                    }
                }

                //jeśli broniąca jednostka ma zginąć, ale ma moc J, to "zatruwa" atakującego
                if(defense.creature.getPower().equals("J")){
                    attack.creature.setHp(1);
                    attack.creature.setPoisoned(1);
                    if(attack.creature.ifUnbroaken())
                        attack.creature.setUnbroaken(0);
                }

                //broniąca się jednostka ginie
                discardeds.putCard(defense.removeCard());
                return true;
            }
        }
        //jeżeli atakująca nie miała mocy X, ale ma moc G, to zbija broniącego, który wraca do ekwipunku właściciela
        else if(attack.creature.getPower().equals("G")){
            G_Creature creature = (G_Creature) attack.creature;
            if(creature.getUse() == 1){
                creature.setUse(0);
                opponent.eq.addCreature(defense.removeCard());
                return true;
            }
        }
        //normalna walka w przypadku nie posiadania mocy X i G
        //jeśli właściciel atakującej jednostki posiada kartę Rage "Crusher", wtedy jego atak jest bardziej zabójczy
        if((you.Crusher == 1 && attack.creature.getAttack() == defense.creature.getHp()) || attack.creature.getAttack() > defense.creature.getHp()){

            //próba ratowania się kartą z mocą U przez broniącego się
            if(defense.creature.getImmortal(opponent, discardeds, this, defense.position))
                return false;

            //próba ratowania się kartą z mocą R przez broniącego się
            if(defense.creature.getHelp(opponent, discardeds, this, defense.position))
                return false;

            //sprawdzenie czy karta broniąca ma moc N i jej jeszcze nie wykorzystała
            if(defense.creature.getPower().equals("N")) {
                N_Creature n = (N_Creature) defense.creature;
                if (n.getUse() == 1) {
                    n.setUse(0);
                    return false;
                }
            }

            //jeśli broniąca jednostka ma zginąć, ale ma moc J, to "zatruwa" atakującego
            if(defense.creature.getPower().equals("J")){
                attack.creature.setHp(1);
                attack.creature.setPoisoned(1);
                if(attack.creature.ifUnbroaken())
                    attack.creature.setUnbroaken(0);
            }

            //broniąca się jednostka ginie
            discardeds.putCard(defense.removeCard());
            return true;
        }
        return false;
    }

    //metoda rozszerzająca funkcję fight o argument wybierający konkretne pola do rozpatrzenia walki
    public void fight(Player you, Player opponent, int target, Discardeds_Stack discardeds){
        if(you.id == 1){
            if(!line2.get(target).empty) {
                if (fight(line1.get(target), line2.get(target), discardeds, you, opponent))
                    opponent.counter--;
            }
            else {
                if(line1.get(target).creature.getPower().equals("F") && target + 1 < 5){
                    if(!line2.get(target + 1).empty) {
                        if (fight(line1.get(target), line2.get(target + 1), discardeds, you, opponent))
                            opponent.counter--;
                    }
                }
            }
        }
        else{
            if(!line1.get(target).empty) {
                if (fight(line2.get(target), line1.get(target), discardeds, you, opponent))
                    opponent.counter--;
            }
            else {
                if(line2.get(target).creature.getPower().equals("F") && target - 1 >= 0){
                    if(!line1.get(target - 1).empty) {
                        if (fight(line2.get(target), line1.get(target - 1), discardeds, you, opponent))
                            opponent.counter--;
                    }
                }
            }
        }
    }


    //metoda umieszcza wybranego stwora na pierwszym polu linii odpowiedniego gracza
    //jednak najpierw sprawdza czy pole i jeśli potrzeba, kolejne pola są zajęte, bo
    //jeżeli są, to przepycha najpierw stwory do przodu, one mają możliwość ataku
    //dopiero wtedy wybrany stwor staje na pierwszym polu i też wykonuje atak, jeśli ma kogo atakować
    public void put(Creature creature, Player you, Player opponent,  Discardeds_Stack discardeds, GameController gameController){
        //wersja dla gracza pierwszego
        if(you.id == 1){
            if(!line1.get(0).empty){
                if(!line1.get(1).empty){
                    if(!line1.get(2).empty){
                        line1.get(3).putCard(line1.get(2).removeCard());
                        //normalna walka
                        if (!line2.get(3).empty) {
                            if (fight(line1.get(3), line2.get(3), discardeds, you, opponent))
                                opponent.counter--;
                        }
                        //jeśli postać ma moc F
                        else{
                            if(line1.get(3).creature.getPower().equals("F")){
                                if (!line2.get(4).empty) {
                                    if (fight(line1.get(3), line2.get(4), discardeds, you, opponent))
                                            opponent.counter--;
                                }
                            }
                        }
                    }
                    line1.get(2).putCard(line1.get(1).removeCard());
                    //normalna walka
                    if (!line2.get(2).empty) {
                        if (fight(line1.get(2), line2.get(2), discardeds, you, opponent))
                            opponent.counter--;
                    }
                    //jeśli postać ma moc F
                    else{
                        if(line1.get(2).creature.getPower().equals("F")){
                            if (!line2.get(3).empty) {
                                if (fight(line1.get(2), line2.get(3), discardeds, you, opponent))
                                    opponent.counter--;
                            }
                        }
                    }
                }
                line1.get(1).putCard(line1.get(0).removeCard());
                //normalna walka
                if (!line2.get(1).empty) {
                    if (fight(line1.get(1), line2.get(1), discardeds, you, opponent))
                        opponent.counter--;
                }
                //jeśli postać ma moc F
                else{
                    if(line1.get(1).creature.getPower().equals("F")){
                        if (!line2.get(2).empty) {
                            if (fight(line1.get(1), line2.get(2), discardeds, you, opponent))
                                    opponent.counter--;
                        }
                    }
                }
            }
            line1.get(0).putCard(creature);
            //normalna walka
            if (!line2.get(0).empty) {
                if (fight(line1.get(0), line2.get(0), discardeds, you, opponent))
                    opponent.counter--;
            }
            //jeśli postać ma moc F
            else{
                if(line1.get(0).creature.getPower().equals("F")){
                    if (!line2.get(1).empty) {
                        if (fight(line1.get(0), line2.get(1), discardeds, you, opponent))
                                opponent.counter--;
                    }
                }
            }
        }
        //wersja dla gracza drugiego
        else{
            if(!line2.get(4).empty){
                if(!line2.get(3).empty){
                    if(!line2.get(2).empty){
                        line2.get(1).putCard(line2.get(2).removeCard());
                        //normalna walka
                        if (!line1.get(1).empty) {
                            if (fight(line2.get(1), line1.get(1), discardeds, you, opponent))
                                opponent.counter--;
                        }
                        //jeśli postać ma moc F
                        else{
                            if(line2.get(1).creature.getPower().equals("F")){
                                if (!line1.get(0).empty) {
                                    if (fight(line2.get(1), line1.get(0), discardeds, you, opponent))
                                        opponent.counter--;
                                }
                            }
                        }
                    }
                    line2.get(2).putCard(line2.get(3).removeCard());
                    //normalna walka
                    if (!line1.get(2).empty) {
                        if (fight(line2.get(2), line1.get(2), discardeds, you, opponent))
                            opponent.counter--;
                    }
                    //jeśli postać ma moc F
                    else{
                        if(line2.get(2).creature.getPower().equals("F")){
                            if (!line1.get(1).empty) {
                                if (fight(line2.get(2), line1.get(1), discardeds, you, opponent))
                                    opponent.counter--;
                            }
                        }
                    }
                }
                line2.get(3).putCard(line2.get(4).removeCard());
                //normalna walka
                if (!line1.get(3).empty) {
                    if (fight(line2.get(3), line1.get(3), discardeds, you, opponent))
                        opponent.counter--;
                }
                //jeśli postać ma moc F
                else {
                    if(line2.get(3).creature.getPower().equals("F")){
                        if (!line1.get(2).empty) {
                            if (fight(line2.get(3), line1.get(2), discardeds, you, opponent))
                                    opponent.counter--;
                        }
                    }
                }
            }
            line2.get(4).putCard(creature);
            //normalna walka
            if (!line1.get(4).empty) {
                if (fight(line2.get(4), line1.get(4), discardeds, you, opponent))
                    opponent.counter--;
            }
            //jeśli postać ma moc F
            else{
                if(line2.get(4).creature.getPower().equals("F")){
                    if (!line1.get(3).empty) {
                        if (fight(line2.get(4), line1.get(3), discardeds, you, opponent))
                            opponent.counter--;
                    }
                }
            }
        }
        exportImagesFromBackend(gameController);
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
    public void endGame(Player you, Player opponent, BufferedWriter out){
        System.out.println("\n" + "\n" + "\n" + "\n" + "\n"  + "\n" + "\n" + "\n" + "\n" + "\n");
        System.out.println("GRA SKONCZONA!");
        System.out.println("Gracz 1 " + you.score + ":" + opponent.score + " Gracz 2");
        //out.println("Gracz 1 " + you.score + ":" + opponent.score + " Gracz 2");
        //out.println("END_GAME");
        System.out.println("ENDGAME");
        /*
        TU NALEŻY WSTAWIĆ FRAGEMNT KODU WYSYŁAJĄCY WYNIKI DO BAZY
        MOŻE NP ŚCIĄGNĄĆ TYCH GRACZY Z TABELI WYNIKI, DODAĆ IM PUNKTY Z TEJ GRY I ZUPDATOWAĆ REKORDY W BAZIE
         */
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
        StringBuilder plansza = new StringBuilder();
        for(int i = 0; i < 5; i++){
            if(line2.get(i).empty)
                plansza.append(" <-- ");
            else
                plansza.append(line2.get(i).creature.onBoard());

            if(i < 4)
                plansza.append(" | ");
            else
                plansza.append("\n");
        }

        plansza.append("-".repeat(37));
        plansza.append("\n");

        for(int i = 0; i < 5; i++){
            if(line1.get(i).empty)
                plansza.append(" --> ");
            else
                plansza.append(line1.get(i).creature.onBoard());

            if(i < 4)
                plansza.append(" | ");
            else
                plansza.append("\n");
        }
        return plansza.toString();
    }





    //pojedyncze pole
    private static class Field{
        public boolean empty;
        public Creature creature;
        public int position;

        public Field(int position){
            empty = true;
            creature = null;
            this.position = position;
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

        //podmienia stwora na tym polu na podanego w argumencie
        public void setCard(Creature creature){this.creature = creature;}
    }
}
