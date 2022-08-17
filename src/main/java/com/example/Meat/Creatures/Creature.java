package Creatures;

import Demo.*;

//Klasa do dziedziczenia z niej i tworzenia stworów bez mocy
public class Creature {

    protected int cost;         //cena
    protected int attack;       //atak
    protected int hp;           //życie
    protected String power;     //moc

    protected int Swarm;        //cecha przyznawana przy wystawieniu, o ile gracz posiada kartę Rage "Swarm"
    protected int Unbroaken;    //cecha przyznawana przy wystawieniu, o ile gracz posiada kartę Rage "Unbroaken"

    protected int poisoned;     //pole okreslajace, czy na ta jednostke zadzialala moc J
    protected int copy_hp;      //pole przechowujące kopię życia. Stwór musi odzyskać swoje początkowe życie gdy spowrotem trafia do czyjegoś ekwipunku.
    
    public Creature(){}

    public Creature(int cost, int attack, int hp){
        this.cost = cost;
        this.attack = attack;
        this.hp = hp;
        this.power = "-";
        this.Swarm = 0;
        this.Unbroaken = 0;
        this.poisoned = 0;
        this.copy_hp = hp;
    }

    public int getCost(){
        return cost;
    }
    public int getAttack(){
        return attack;
    }
    public int getHp(){
        return hp;
    }
    public String getPower(){
        return power;
    }

    public void increaseAttack(){attack++;}
    public void decreaseAttack(){attack--;}

    public void setSwarm(int value){Swarm = value;}
    public boolean ifSwarm(){return Swarm == 1;}

    public void increaseHp(){hp++;}
    public void decreaseHp(){hp--;}

    public boolean ifPoisoned(){return poisoned == 1;}
    public void setHp(int val){hp = val;}
    public void setPoisoned(int val){
        poisoned = val;
        if(val == 0)
            hp = copy_hp;
    }

    public void setUnbroaken(int value){Unbroaken = value;}
    public boolean ifUnbroaken(){return Unbroaken == 1;}

    //metoda do przeciążania przez stwory z mocami
    public void effect(Player you, Player opponent, Cards_Stack cards, Discardeds_Stack discardeds, Money money, Board board){}

    //porównywanie dwóch stworów
    public boolean theSame(Creature c) {

        if(this.getCost() != c.getCost())
            return false;

        if(this.getAttack() != c.getAttack())
            return false;

        if(this.getHp() != c.getHp())
            return false;

        return this.getPower().equals(c.getPower());
    }

    //metoda szukająca stworów z mocą R
    //gdy takiego znajdzie usuwa go z planszy
    //jest to implementacja mocy R
    //wykorzystana przy walkach
    public boolean getHelp(Player you, Discardeds_Stack discardeds, Board board, int position){
        if(you.id == 1){
            for(int i = 0; i < 5; i++){
                if(!board.empty(you.id, i) && i != position) {
                    if (board.getCreature(you.id, i).getPower().equals("R")) {
                        discardeds.putCard(board.removeCard(you.id, i));
                        you.counter--;
                        return true;
                    }
                }
            }
        }
        else{
            for(int i = 4; i >= 0; i--){
                if(!board.empty(you.id, i) && i != position) {
                    if (board.getCreature(you.id, i).getPower().equals("R")) {
                        discardeds.putCard(board.removeCard(you.id, i));
                        you.counter--;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    //metoda szukająca stworów z mocą U na dwóch kolejnych polach danego gracza
    //zwraca true gdy znajdzie, false w przeciwnym wypadku
    //jest to implementacja mocy U
    //wykorzystana przy walkach
    public boolean getImmortal(Player you, Discardeds_Stack discardeds, Board board, int position){
        if(you.id == 1){
            for(int i = 1; i <= 2; i++){
                if(position + i > 4)
                    break;
                if(!board.empty(you.id, position + i)){
                    if(board.getCreature(you.id, position + i).getPower().equals("U")){
                        return true;
                    }
                }
            }
        }
        else{
            for(int i = 1; i <= 2; i++){
                if(position - i < 0)
                    break;
                if(!board.empty(you.id, position - i)){
                    if(board.getCreature(you.id, position - i).getPower().equals("U")){
                        return true;
                    }
                }
            }
        }
        return false;
    }


    //wypisywanie statystyk na potrzeby testu, normalnie będą obrazki
    @Override
    public String toString(){
        String staty = "koszt: " + cost;
        staty += " | atak: " + attack;
        staty += " | hp: " + hp;
        staty += " | moc: " + power;
        return  staty;
    }

    //skrócona wersja statystyk do wypisywania postaci na planszy
    public String onBoard(){
        return attack + "/" + hp + "/" + power;
    }
}
