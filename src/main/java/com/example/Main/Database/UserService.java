package com.example.Main.Database;

import com.example.Main.TestHibernate;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

//Klasa do obslugi klasy User
public class UserService
{
    public User findOne(long id)
    {
        EntityManager entityManager = TestHibernate.getFactory().createEntityManager();
        return entityManager.find(User.class, id); //User.class bo inaczej trzeba tworzyc zmienna a tak nie trzeba, taki smaczek :p
    }

    public void addUser(String username, String password)
    {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        EntityManager entityManager = TestHibernate.getFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin(); //Przed wprowadzaniem zmian w bazie otwieram tranzakcje
        //persist jesli dodajemy kompletnie nowy obiekt
        //merge jesli edytujemy albo obiekt juz istnial
        entityManager.persist(user);
        entityTransaction.commit(); //Po dokonaniu zmian w bazi, zatwierdzam je
    }
}
