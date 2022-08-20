package com.example.Main.Database;

import com.example.Main.Login.StartLoginApplication;
import com.example.Main.Database.Test;
import com.example.Main.TestMechaniki;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class TestService
{
    //Nie dziala
    public void addUser(String imie, String nazwisko)
    {
        Test test = new Test();
        test.setImie(imie);
        test.setNazwisko(nazwisko);
        EntityManager entityManager = TestMechaniki.getFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin(); //Przed wprowadzaniem zmian w bazie otwieram tranzakcje
        //persist jesli dodajemy kompletnie nowy obiekt
        //merge jesli edytujemy albo obiekt juz istnial
        entityManager.persist(test);
        entityTransaction.commit(); //Po dokonaniu zmian w bazi, zatwierdzam je
    }

    public Test findOne(long id) {
        EntityManager entityManager = TestMechaniki.getFactory().createEntityManager();
        return entityManager.find(Test.class, id);
    }
}
