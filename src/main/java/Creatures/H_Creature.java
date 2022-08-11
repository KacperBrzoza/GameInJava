package Creatures;

import Demo.*;

//gdy wystawisz tego stwora, dobierasz stwora ze stosu kart odrzuconych
public class H_Creature extends Creature{
    public H_Creature(int cost, int attack, int hp){
        this.cost = cost;
        this.attack = attack;
        this.hp = hp;
        this.power = "H";
        this.Swarm = 0;         //cecha przyznawana przy wystawieniu, o ile gracz posiada kartę Rage "Swarm"
        this.Unbroaken = 0;     //cecha przyznawana przy wystawieniu, o ile gracz posiada kartę Rage "Unbroaken"
        this.poisoned = 0;      //pole okreslajace, czy na ta jednostke zadzialala moc J
    }

    @Override
    public void effect(Player you, Player opponent, Cards_Stack cards, Discardeds_Stack discardeds, Money money, Board board) {
        if(!discardeds.empty())
            you.eq.addCreature(discardeds.takeCard());
    }
}
