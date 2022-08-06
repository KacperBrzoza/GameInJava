package Rage_Cards;

import Creatures.Creature;
import Demo.*;

public class BraveMove extends R_Card{

    public BraveMove(){
        name = "Brave Move";
        description = "wrogie jednostki cofają się 1 pole, a twoje przesuwają się o 1 pole do przodu";
    }

    //UWAGA! Jeśli zdarzy się, że jednostka "nie ma dokąd się cofnąć", wraca wtedy do eq posiadacza
    //jednak właściel dostaje rekompensatę połowy ceny tego stwora
    public void effect(Player you, Player opponent, Board board, Discardeds_Stack discardeds, Cards_Stack cards, Money money, Rage_Cards rage){
        if(you.id == 1){
            //jeżeli przeciwnik ma coś wystawione
            if(opponent.counter > 0){
                //tu jest cofka do eq
                if(!board.empty(opponent.id, 4)){
                    Creature creature = board.removeCard(opponent.id, 4);
                    opponent.eq.addCreature(creature);
                    opponent.money += creature.getCost()/2;
                    opponent.counter--;
                }
                //cofnięcie pozostałych kart przeciwnika
                for(int i = 3; i >=0; i--){
                    if(!board.empty(opponent.id, i))
                        board.insertCard(board.removeCard(opponent.id, i), i+1, opponent.id);
                }
            }
            //jeżeli aktualny gracz ma coś wystawione
            if(you.counter > 0){
                //może się stać, że przy użyciu tej karty jeden gracz straci tarczę, tu jest ten przypadek
                if(!board.empty(you.id, 4)){
                    discardeds.putCard(board.removeCard(you.id, 4));
                    you.counter--;
                    opponent.loseShield();
                    you.score += 1.0;
                    opponent.score -= 0.5;
                    R_Card rage_card = rage.giveCard();
                    opponent.rage.putCard(rage_card);
                    System.out.println("GRACZ " + opponent.id + " otrzymał kartę *" + rage_card + "*");
                    rage_card.effect(opponent, you, board, discardeds, cards, money, rage);
                    System.out.println("\n\nKontynuacja działania BraveMove w wykonaniu GRACZA " + you.id);
                }
                //przesunięcie pozostałych kart do przodu
                for(int i = 3; i >=0; i--){
                    if(!board.empty(you.id, i))
                        board.insertCard(board.removeCard(you.id, i), i+1, you.id);
                }
            }
        }
        else{
            //jeżeli przeciwnik ma coś wystawione
            if(opponent.counter > 0){
                //tu jest cofka do eq
                if(!board.empty(opponent.id, 0)){
                    Creature creature = board.removeCard(opponent.id, 0);
                    opponent.eq.addCreature(creature);
                    opponent.money += creature.getCost()/2;
                    opponent.counter--;
                }
                //cofnięcie pozostałych kart przeciwnika
                for(int i = 1; i < 5; i++){
                    if(!board.empty(opponent.id, i))
                        board.insertCard(board.removeCard(opponent.id, i), i-1, opponent.id);
                }
            }
            //jeżeli aktualny gracz ma coś wystawione
            if(you.counter > 0){
                //może się stać, że przy użyciu tej karty jeden gracz straci tarczę, tu jest ten przypadek
                if(!board.empty(you.id, 0)){
                    discardeds.putCard(board.removeCard(you.id, 0));
                    you.counter--;
                    opponent.loseShield();
                    you.score += 1.0;
                    opponent.score -= 0.5;
                    R_Card rage_card = rage.giveCard();
                    opponent.rage.putCard(rage_card);
                    System.out.println("GRACZ " + opponent.id + " otrzymał kartę *" + rage_card + "*");
                    rage_card.effect(opponent, you, board, discardeds, cards, money, rage);
                    System.out.println("\n\nKontynuacja działania BraveMove w wykonaniu GRACZA " + you.id);
                }
                //przesunięcie pozostałych kart do przodu
                for(int i = 1; i < 5; i++){
                    if(!board.empty(you.id, i))
                        board.insertCard(board.removeCard(you.id, i), you.id, i-1);
                }
            }
        }
        System.out.println(board);
    }
}
