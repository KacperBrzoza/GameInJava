package Rage_Cards;

import Demo.*;

import java.util.Scanner;

public class Redeployment extends R_Card{

    public Redeployment(){
        name = "Redeployment";
        description = "możesz cofnąć swoją wybraną jednostkę do ekwipunku, jeśli to zrobisz zyskasz 3 żetony waluty (jednorazowe)";
    }

    public void effect(Player you, Player opponent, Board board, Discardeds_Stack discardeds, Cards_Stack cards, Money money, Rage_Cards rage){
        if(you.counter > 0){
            System.out.println("\n" + "\n" + "\n" + "\n" + "\n"  + "\n" + "\n" + "\n" + "\n" + "\n");
            System.out.println("GRACZ " + you.id + " MOŻE WYCOFAĆ SWOJEGO STWORA!!!");
            System.out.println(board);
            for(int i = 0; i < 5; i++){
                if(!board.empty(you.id, i)){
                    System.out.print("  (" + i + ")  ");
                }
            }
            System.out.println("\n(5) aby zrezygnować");
            Scanner scan = new Scanner(System.in);
            int number = -1;
            while (number < 0 || number > 5){
                System.out.print("wybierz: ");
                number = scan.nextInt();
                if(number >=0 && number < 5) {
                    if (!board.empty(you.id, number)) {
                        you.eq.addCreature(board.removeCard(you.id, number));
                        you.counter--;
                        for(int i = 0; i < 3; i++)
                            you.money += money.giveMoney();
                    }
                    else{
                        number = -1;
                    }
                }
                else if(number == 5){
                    System.out.print("zrezygnowano ");
                }
            }
            System.out.println("wybierz dowolną liczbę, aby kontynuować... ");
            number = scan.nextInt();
        }
    }
}
