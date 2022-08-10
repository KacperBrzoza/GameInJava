package Creatures;

import Demo.*;

//gdy wystawisz tego stwora, dobierasz żeton waluty
public class M_Creature extends Creature{
    public M_Creature(int cost, int attack, int hp){
        this.cost = cost;
        this.attack = attack;
        this.hp = hp;
        this.power = "M";
        this.Swarm = 0;         //cecha przyznawana przy wystawieniu, o ile gracz posiada kartę Rage "Swarm"
        this.Unbroaken = 0;     //cecha przyznawana przy wystawieniu, o ile gracz posiada kartę Rage "Unbroaken"
    }

    @Override
    public void effect(Player you, Player opponent, Cards_Stack cards, Discardeds_Stack discardeds, Money money, Board board) {
        you.money += money.giveMoney(you, opponent);
    }
}
