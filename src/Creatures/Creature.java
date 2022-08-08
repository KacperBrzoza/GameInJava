package Creatures;

//Klasa do dziedziczenia z niej
public class Creature {

    private final int cost;
    private int attack;
    private int hp;
    private final String power;
    private int Swarm;
    private int Unbroaken;

    public Creature(int cost, int attack, int hp){
        this.cost = cost;
        this.attack = attack;
        this.hp = hp;
        this.power = "brak";
        this.Swarm = 0;         //cecha przyznawana przy wystawieniu, o ile gracz posiada kartę Rage "Swarm"
        this.Unbroaken = 0;     //cecha przyznawana przy wystawieniu, o ile gracz posiada kartę Rage "Unbroaken"
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
    public void setUnbroaken(int value){Unbroaken = value;}
    public boolean ifUnbroaken(){return Unbroaken == 1;}

    //porównywanie dwóch stworów
    public boolean theSame(Creature c) {

        if(this.getCost() != c.getCost())
            return false;

        if(this.getAttack() != c.getAttack())
            return false;

        if(this.getHp() != c.getHp())
            return false;

        if(!this.getPower().equals(c.getPower()))
            return false;

        return true;
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
