package Creatures;

//stwory z tą mocą unikają swojej pierwszej śmierci
public class N_Creature extends Creature{

    private int use;        //pole przechowujące informację, czy moc tego stwora została zużyta

    public N_Creature(int cost, int attack, int hp){
        this.cost = cost;
        this.attack = attack;
        this.hp = hp;
        this.power = "N";
        this.Swarm = 0;
        this.Unbroaken = 0;
        this.use = 1;
        this.poisoned = 0;
    }

    public int getUse(){return use;}
    public void setUse(int val){use = val;}

}
