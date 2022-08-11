package Creatures;

//te stwory, jeżeli nie spotkają przeciwnika do walki, to próbują walczyć z przeciwnikiem na jednym polu dalej
public class F_Creature extends Creature{

    public F_Creature(int cost, int attack, int hp){
        this.cost = cost;
        this.attack = attack;
        this.hp = hp;
        this.power = "F";
        this.Swarm = 0;         //cecha przyznawana przy wystawieniu, o ile gracz posiada kartę Rage "Swarm"
        this.Unbroaken = 0;     //cecha przyznawana przy wystawieniu, o ile gracz posiada kartę Rage "Unbroaken"
        this.poisoned = 0;      //pole okreslajace, czy na ta jednostke zadzialala moc J
    }
}
