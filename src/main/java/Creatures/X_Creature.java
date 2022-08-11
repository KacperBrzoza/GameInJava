package Creatures;

public class X_Creature extends Creature{

    private int use;

    public X_Creature(int cost, int attack, int hp){
        this.cost = cost;
        this.attack = attack;
        this.hp = hp;
        this.power = "X";
        this.Swarm = 0;         //cecha przyznawana przy wystawieniu, o ile gracz posiada kartę Rage "Swarm"
        this.Unbroaken = 0;     //cecha przyznawana przy wystawieniu, o ile gracz posiada kartę Rage "Unbroaken"
        this.use = 1;
        this.poisoned = 0;      //pole okreslajace, czy na ta jednostke zadzialala moc J
    }

    public int getUse(){return use;}
    public void setUse(int val){use = val;}
}
