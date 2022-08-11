package Creatures;

//gdy inny stwór ma zginąć, ten zginie za niego
public class R_Creature extends Creature{

    public R_Creature(int cost, int attack, int hp){
        this.cost = cost;
        this.attack = attack;
        this.hp = hp;
        this.power = "R";
        this.Swarm = 0;         //cecha przyznawana przy wystawieniu, o ile gracz posiada kartę Rage "Swarm"
        this.Unbroaken = 0;     //cecha przyznawana przy wystawieniu, o ile gracz posiada kartę Rage "Unbroaken"
        this.poisoned = 0;      //pole okreslajace, czy na ta jednostke zadzialala moc J
    }
}
