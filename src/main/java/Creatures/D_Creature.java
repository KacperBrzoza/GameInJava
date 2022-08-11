package Creatures;

import Demo.*;

//gdy wystawisz tego stwora, dobierasz kartę stwora
public class D_Creature extends Creature{

    public D_Creature(int cost, int attack, int hp){
        this.cost = cost;
        this.attack = attack;
        this.hp = hp;
        this.power = "D";
        this.Swarm = 0;         //cecha przyznawana przy wystawieniu, o ile gracz posiada kartę Rage "Swarm"
        this.Unbroaken = 0;     //cecha przyznawana przy wystawieniu, o ile gracz posiada kartę Rage "Unbroaken"
        this.poisoned = 0;      //pole okreslajace, czy na ta jednostke zadzialala moc J
    }

    @Override
    public void effect(Player you, Player opponent, Cards_Stack cards, Discardeds_Stack discardeds, Money money, Board board) {
        you.eq.addCreature(cards.giveCard());
    }
}
