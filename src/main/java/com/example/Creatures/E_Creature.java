package Creatures;

import Demo.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class E_Creature extends Creature{

    public E_Creature(int cost, int attack, int hp){
        this.cost = cost;
        this.attack = attack;
        this.hp = hp;
        this.power = "E";
        this.Swarm = 0;         //cecha przyznawana przy wystawieniu, o ile gracz posiada kartę Rage "Swarm"
        this.Unbroaken = 0;     //cecha przyznawana przy wystawieniu, o ile gracz posiada kartę Rage "Unbroaken"
    }

    @Override
    public void effect(Player you, Player opponent, Cards_Stack cards, Discardeds_Stack discardeds, Money money, Board board) {
        //jeżeli aktualny gracz ma jakieś stwory z mocą E i nie ma 4 jednostek na planszy
        List<Creature> e_creatures = new ArrayList<>();
        if(you.eq.size() > 0){
            for(int i = 0; i < you.eq.size(); i++){
                if(you.eq.ifE(i)){
                    e_creatures.add(you.eq.copy(i));
                }
            }
        }
        //jeżeli aktualny gracz ma jakieś stwory z mocą E i nie ma 4 jednostek na planszy
        if(e_creatures.size() >= 1 && you.counter < 4){
            System.out.println("Możesz za darmo wystawić jednostkę z mocą E");
            System.out.println(board);
            for(int i = 0; i < e_creatures.size(); i++)
                System.out.println("(" + i + ") " + e_creatures.get(i));
            System.out.println("(" + e_creatures.size() + ") nie używaj mocy E");
            int number = -1;
            Scanner scan = new Scanner(System.in);
            while (number < 0 || number > e_creatures.size()){
                System.out.print("wybierz: ");
                number = scan.nextInt();
                //tu następuje właściwy wybór
                if(number >=0 && number < e_creatures.size()){
                    Creature creature = e_creatures.get(number);
                    //dodatkowe cechy jeśli gracz posiada odpowiednie karty Rage
                    if(you.Swarm == 1){
                        creature.setSwarm(1);
                        creature.increaseAttack();
                    }
                    if(you.Unbroaken == 1){
                        creature.setUnbroaken(1);
                        creature.increaseHp();
                    }
                    board.put(creature, you, opponent, discardeds);
                    for(int i = 0; i < you.eq.size(); i++){
                        if(you.eq.copy(i).theSame(creature)){
                            you.eq.pickCreature(i);
                            break;
                        }
                    }
                    you.counter++;
                    creature.effect(you, opponent, cards, discardeds, money, board);
                }
                else if(number == you.eq.size())
                    break;
            }
        }
    }
}
