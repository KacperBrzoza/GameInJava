package Creatures;

//gdy stwór z tą mocą zginie, obniża hp swojego zabójcy do 1
public class J_Creature extends Creature{
    public J_Creature(int cost, int attack, int hp){
        this.cost = cost;
        this.attack = attack;
        this.hp = hp;
        this.power = "J";
        this.Swarm = 0;
        this.Unbroaken = 0;
        this.poisoned = 0;
    }
}
