package Creatures;

public class U_Creature extends Creature{

    public U_Creature(int cost, int attack, int hp){
        this.cost = cost;
        this.attack = attack;
        this.hp = hp;
        this.power = "U";
        this.Swarm = 0;         //cecha przyznawana przy wystawieniu, o ile gracz posiada kartę Rage "Swarm"
        this.Unbroaken = 0;     //cecha przyznawana przy wystawieniu, o ile gracz posiada kartę Rage "Unbroaken"
        this.poisoned = 0;      //pole okreslajace, czy na ta jednostke zadzialala moc J
    }
}
