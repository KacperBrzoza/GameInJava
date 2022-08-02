package Creatures;

public class Creature {

    private final int cost;
    private final int attack;
    private final int hp;
    private final String power;
    //image;

    public Creature(int cost, int attack, int hp){
        this.cost = cost;
        this.attack = attack;
        this.hp = hp;
        this.power = "brak";
    }

    //wypisywanie statystyk na potrzeby testu, normalnie będą obrazki
    @Override
    public String toString(){
        String staty = "koszt: " + cost;
        staty += " | atak: " + attack;
        staty += " | hp: " + hp;
        staty += " | moc: " + power + "\n";
        return  staty;
    }
}
