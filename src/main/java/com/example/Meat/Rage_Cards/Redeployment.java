package com.example.Meat.Rage_Cards;

import com.example.Meat.Demo.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Redeployment extends R_Card{

    public Redeployment(){
        name = "Redeployment";
        description = "mozesz cofnac swoja wybrana jednostke do ekwipunku, jesli to zrobisz zyskasz 3 zetony waluty (jednorazowe)";
    }

    public void effect(Player you, Player opponent, Board board, Discardeds_Stack discardeds, Cards_Stack cards, Money money, Rage_Cards rage, PrintWriter out, BufferedReader in) throws IOException {

        //zadziała gdy masz coś wystawione
        if(you.counter > 0){

            if(you.id == 1){
                System.out.println("\n" + "\n" + "\n" + "\n" + "\n"  + "\n" + "\n" + "\n" + "\n" + "\n");
                System.out.println("MOZESZ WYCOFAC SWOJEGO STWORA!!!");
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
                        //zabranie z planszy dobrze wybranej karty
                        if (!board.empty(you.id, number)) {
                            you.eq.addCreature(board.removeCard(you.id, number));
                            you.counter--;
                            //dobranie 3 żetonów waluty
                            for(int i = 0; i < 3; i++)
                                you.money += money.giveMoney(you, opponent);
                        }
                        else{
                            number = -1;
                        }
                    }
                    else if(number == 5){
                        System.out.println("zrezygnowano ");
                    }
                }
            }
            else{
                String fromCLient;
                out.println("\n" + "\n" + "\n" + "\n" + "\n"  + "\n" + "\n" + "\n" + "\n" + "\n");
                out.println("MOZESZ WYCOFAC SWOJEGO STWORA!!!");
                out.println(board);
                for(int i = 0; i < 5; i++){
                    if(!board.empty(you.id, i)){
                        out.print("  (" + i + ")  ");
                    }
                }
                out.println("\n(5) aby zrezygnować");

                int number = -1;
                while (number < 0 || number > 5){
                    out.println("5");
                    fromCLient = in.readLine();
                    number = Integer.parseInt(fromCLient);

                    if(number >=0 && number < 5) {
                        //zabranie z planszy dobrze wybranej karty
                        if (!board.empty(you.id, number)) {
                            you.eq.addCreature(board.removeCard(you.id, number));
                            you.counter--;
                            //dobranie 3 żetonów waluty
                            for(int i = 0; i < 3; i++)
                                you.money += money.giveMoney(you, opponent);
                        }
                        else{
                            number = -1;
                        }
                    }
                    else if(number == 5){
                        out.println("zrezygnowano ");
                    }
                }
            }
        }
    }
}
