package Creatures;

public class Creature {

    private final int cost;
    private final int attack;
    private final int hp;
    private final String power;

    public Creature(int cost, int attack, int hp){
        this.cost = cost;
        this.attack = attack;
        this.hp = hp;
        this.power = "brak";
    }

    public int getAttack(){
        return attack;
    }

    public int getHp(){
        return hp;
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
        String staty = attack + "/" + hp + "/" + "power";
        return staty;
    }
}
