package Creatures;

//stwory z tą mocą, gdy mają zginąć pierwszy raz, pojawiają się na 1 polu, tak jakby były dopiero wystawione
public class A_Creature extends Creature{

    //pole, które po pierwszej "śmierci" zeruje się i od tej pory pozbawia jednostkę mocy A
    protected int chance;

    public A_Creature(int cost, int attack, int hp){
        this.cost = cost;
        this.attack = attack;
        this.hp = hp;
        this.power = "A";
        this.Swarm = 0;         //cecha przyznawana przy wystawieniu, o ile gracz posiada kartę Rage "Swarm"
        this.Unbroaken = 0;     //cecha przyznawana przy wystawieniu, o ile gracz posiada kartę Rage "Unbroaken"
        this.chance = 0;
    }

    public int getChance(){return chance;}
    public void setChance(int val){chance = val;}
}
