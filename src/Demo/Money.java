package Demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//stos żetonów waluty
public class Money {
    private final List<Integer> bank;

    //inicjalizacja "stosu" żetonów waluty
    //13 jeddynek
    //15 dwójek
    //10 trójek
    //6 czwórek
    //4 piątki
    //2 szóstki
    private void initialization(){
        for(int i = 0; i < 13; i++){
            bank.add(1);
        }
        for(int i = 0; i < 15; i++){
            bank.add(2);
        }
        for(int i = 0; i < 10; i++){
            bank.add(3);
        }
        for(int i = 0; i < 6; i++){
            bank.add(4);
        }
        for(int i = 0; i < 4; i++){
            bank.add(5);
        }
        for(int i = 0; i < 2; i++){
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
    public int giveMoney(){
        Random rand = new Random();
        int n = rand.nextInt(bank.size());

        int money = bank.get(n);

        bank.remove(n);

        //jeżeli bank się opróżni, zostanie ponownie wypełniony
        if(bank.size() == 0)
            initialization();

        return money;
    }

}
