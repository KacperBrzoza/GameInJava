package com.example.Meat.Demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//stos żetonów waluty
public class Money {
    private final List<Integer> bank;

    //inicjalizacja "stosu" żetonów waluty
    //6 jeddynek
    //8 dwójek
    //5 trójek
    //3 czwórki
    //2 piątki
    //1 szóstka
    private void initialization(){
        for(int i = 0; i < 6; i++){
            bank.add(1);
        }
        for(int i = 0; i < 8; i++){
            bank.add(2);
        }
        for(int i = 0; i < 5; i++){
            bank.add(3);
        }
        for(int i = 0; i < 3; i++){
            bank.add(4);
        }
        for(int i = 0; i < 2; i++){
            bank.add(5);
        }
        for(int i = 0; i < 1; i++){
            bank.add(6);
        }
    }

    //konstruktor tworzy listę i ją zapełnia
    public Money(){
        bank = new ArrayList<>();
        initialization();
    }

    //poniższa metoda losuje żeton waluty i go zwraca
    //ma służyć do dobierania żetonów waluty przez graczy
    //potrzebuje dwóch graczy jako parametrów do sprawdzenia posiadania karty Rage "Thief"
    public int giveMoney(Player you, Player opponent){
        Random rand = new Random();
        int n = rand.nextInt(bank.size());

        int money = bank.get(n);

        bank.remove(n);

        //jeżeli bank się opróżni, zostanie ponownie wypełniony
        if(bank.size() == 0) {
            initialization();

            //działanie karty Rage "Thief"
            if(you.Thief == 1){
                for (int i = 0; i < 3; i++)
                    you.money += this.giveMoney(you, opponent);
                System.out.println("Gracz " + you.id + " okradl bank!");
            }
            else if(opponent.Thief == 1){
                for (int i = 0; i < 3; i++)
                    opponent.money += this.giveMoney(you, opponent);
                System.out.println("Gracz " + opponent.id + " okradl bank!");
            }
        }
        return money;
    }

}
