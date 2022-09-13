package com.example.Meat.Creatures;

import com.example.Main.Game.GameController;
import com.example.Meat.Demo.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.PrintWriter;

//gdy wystawisz tego stwora, dobierasz kartę stwora
public class D_Creature extends Creature{

    public D_Creature(int cost, int attack, int hp, String path){
        this.cost = cost;
        this.attack = attack;
        this.hp = hp;
        this.power = "D";
        this.Swarm = 0;
        this.Unbroaken = 0;
        this.poisoned = 0;
        this.path = path;
    }

    @Override
    public void effect(Player you, Player opponent, Cards_Stack cards, Discardeds_Stack discardeds, Money money, Board board, BufferedWriter out, BufferedReader in, GameController gameController) {
        Creature creature;
        if(you.id == 1) {
            creature = cards.giveCard(out, you, opponent, gameController);
            you.eq.addCreature(creature);                                                                                       //dodanie stwora do eq gracza 1
            GameController.addImageToEQ(gameController.eqImages, creature.path);
        }
        else {
            creature = cards.giveCard(out, you, opponent, gameController);
            you.eq.addCreature(creature);
            GameController.server.sendMessageToClient("PATH_" + creature.path);
        }
    }
}
