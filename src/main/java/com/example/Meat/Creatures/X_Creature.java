package Creatures;

//zabija pierwszego napotkanego przeciwnika
public class X_Creature extends Creature{

    private int use;        //pole przechowujące informację, czy moc tego stwora została zużyta

    public X_Creature(int cost, int attack, int hp){
        this.cost = cost;
        this.attack = attack;
        this.hp = hp;
        this.power = "X";
        this.Swarm = 0;
        this.Unbroaken = 0;
        this.use = 1;
        this.poisoned = 0;
    }

    public int getUse(){return use;}
    public void setUse(int val){use = val;}
}
