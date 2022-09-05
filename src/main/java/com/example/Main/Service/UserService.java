package com.example.Main.Service;

import com.example.Main.Model.UserData;
import com.example.Main.PersistenceManager.PersistenceManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.Iterator;
import java.util.List;

public class UserService
{
    public ObservableList<UserData> observableList;

    //Dodowanie uzytkwnika do bazy
    public void add_user(String username, String password)
    {
        UserData userData = new UserData();
        userData.setUsername(username);
        userData.setPassword(password);
        EntityManager entityManager = PersistenceManager.getFactory().createEntityManager();
        EntityTransaction transaction =entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(userData);
        transaction.commit();
    }

    //Funkcja potrzebna do wyswietla nickow graczy - Nie dziala wyswietlanie w rankinu
    //TODO
    /*
    public ObservableList<UserData> show_user()
    {
        EntityManager entityManager = PersistenceManager.getFactory().createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        //transaction.begin();
        Query query = entityManager.createQuery("SELECT c FROM UserData c");
        ObservableList<UserData> list = FXCollections.observableArrayList(query.getResultList());
        //transaction.commit();
        //return list;
        //System.out.println(observableList.toString());
    }

     */
    //TODO
    public void show_user()
    {
        observableList = FXCollections.observableArrayList();
        EntityManager entityManager = PersistenceManager.getFactory().createEntityManager();
        Query query = entityManager.createQuery("SELECT c FROM UserData c");
        Iterator ite = query.getResultList().iterator();
        while (ite.hasNext())
        {
            UserData userData = (UserData)ite.next();
            observableList.add(userData);
        }
    }

    public UserData getUser(String username)
    {
        UserData userData;
        EntityManager entityManager = PersistenceManager.getFactory().createEntityManager();
        try
        {
            userData = (UserData) entityManager.createQuery("SELECT u FROM UserData u WHERE u.username = :username")
                    .setParameter("username", username)
                    .getSingleResult();
        }
        catch (NoResultException ex)
        {
            userData = null;
        }
        return userData;
    }

    //Sprawdzanie czy user jest juz w bazie
    public boolean isUsernameInUse(String username)
    {
        boolean user = true;

        if(getUser(username) == null)
        {
            user = false;
        }
        return user;
    }


    public String getPassword(String username)
    {
        EntityManager entityManager = PersistenceManager.getFactory().createEntityManager();
        Query query = entityManager.createQuery("SELECT c.password FROM UserData c WHERE c.username = :username");
        query.setParameter("username", username);
        String result = query.getSingleResult().toString();
        return result;
    }

    public boolean isCorrect(String username, String password)
    {
        boolean correct = false;
        if(getPassword(username).equals(password))
        {
            correct = true;
        }
        return correct;
    }

    public List<UserData> findAll()
    {
        EntityManager entityManager = PersistenceManager.getFactory().createEntityManager();
        Query query = entityManager.createQuery("select c from UserData c");
        return query.getResultList();
    }

}
