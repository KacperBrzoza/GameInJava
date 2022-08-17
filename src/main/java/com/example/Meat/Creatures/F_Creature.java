package Creatures;

//te stwory, jeżeli nie spotkają przeciwnika do walki, to próbują walczyć z przeciwnikiem na jednym polu dalej
public class F_Creature extends Creature{

    public F_Creature(int cost, int attack, int hp){
        this.cost = cost;
        this.attack = attack;
        this.hp = hp;
        this.power = "F";
        this.Swarm = 0;
        this.Unbroaken = 0;
        this.poisoned = 0;
    }
}
