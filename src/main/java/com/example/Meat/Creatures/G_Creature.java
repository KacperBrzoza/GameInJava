package com.example.Meat.Creatures;

//pierwszy przeciwnik napotkany przez tego stwora wraca do ekwipunku rywala
public class G_Creature extends Creature{

    private int use;    //pole przechowujące informację, czy moc tego stwora została zużyta

    public G_Creature(int cost, int attack, int hp, String path){
        this.cost = cost;
        this.attack = attack;
        this.hp = hp;
        this.power = "G";
        this.Swarm = 0;
        this.Unbroaken = 0;
        this.use = 1;
        this.poisoned = 0;
        this.path = path;
    }

    public int getUse(){return use;}
    public void setUse(int val){use = val;}

    /*
    UWAGA BUG - MOC G POWINNA INGOROWAĆ MOC N I ODSYŁAĆ TAKIE JEDNOSTKI SPOWROTEM DO EKWIPUNKU
    NALEŻY SPRAWDZIĆ CZY use JEST POPRAWNIE ODŚWIEŻANE
     */
}
