package com.example.Main.Service;

import com.example.Main.Model.IpTable;
import com.example.Main.Model.Scores;
import com.example.Main.Model.UserData;
import com.example.Main.PersistenceManager.PersistenceManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

import static com.example.Main.Menu.WaitingEnemyController.SWITCHER;

public class UserService
{
    //Dodowanie uzytkwnika do bazy
    public void add_user(String username, String password)
    {
        UserData userData = new UserData();
        userData.setUsername(username);
        userData.setPassword(password);
        EntityManager entityManager = PersistenceManager.getFactory().createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(userData);
        transaction.commit();
    }

    //Sprawdzanie IP w bazie i na tej podstawie ustawianie SWITCHERA
    public void check_in_base_IP(String IP)
    {
        IpTable ipTable = new IpTable();
        EntityManager entityManager = PersistenceManager.getFactory().createEntityManager();
        Query query = entityManager.createQuery("select i FROM IpTable i");
        if(query.getResultList().isEmpty())
        {
            SWITCHER = 1;
            ipTable= new IpTable();
            ipTable.setIpAddress(IP);
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(ipTable);
            transaction.commit();
            System.out.println("Jestem switcherem 1");
        }
        else
        {
            SWITCHER = 2;
            System.out.println("Jestem switcherem 2");
        }
    }

    //Usuwanie IP
    public void delete_IP()
    {
        EntityManager entityManager = PersistenceManager.getFactory().createEntityManager();
        IpTable ipTable = entityManager.find(IpTable.class, 1);
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.remove(ipTable);
        transaction.commit();
    }

    //Pobieranie IP
    public String get_Ip()
    {
        EntityManager entityManager = PersistenceManager.getFactory().createEntityManager();
        Query query = entityManager.createQuery("SELECT i.ipAddress FROM IpTable i WHERE i.ipid = 1");
        return query.getResultList().toString().replace("[", "").replace("]", "").replace(" ","");
    }

    //Do wysweitlania nickow graczy w rankingu
    public ObservableList<Scores> getAll()
    {
        ObservableList<Scores> observableList = FXCollections.observableArrayList();
        EntityManager entityManager = PersistenceManager.getFactory().createEntityManager();
        List<Scores> uList = entityManager.createQuery("select s FROM Scores s", Scores.class).getResultList();
        observableList.addAll(uList);
        return observableList;
    }

    //Sprawdzanie czy podany uzytkownik jest w bazie
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

    //Sprawdzanie hasla dla uzytkownika
    public String getPassword(String username)
    {
        EntityManager entityManager = PersistenceManager.getFactory().createEntityManager();
        Query query = entityManager.createQuery("SELECT c.password FROM UserData c WHERE c.username = :username");
        query.setParameter("username", username);
        String result = query.getSingleResult().toString();
        return result;
    }

    //Sprawdzanie poprawnosci
    public boolean isCorrect(String username, String password)
    {
        boolean correct = false;
        if(getPassword(username).equals(password))
        {
            correct = true;
        }
        return correct;
    }

    //Sprawdzam czy user jest true czy false, tzn. zalogowany czy nie
    public Boolean check_User(String username)
    {
        EntityManager entityManager = PersistenceManager.getFactory().createEntityManager();
        Query query = entityManager.createQuery("SELECT u.isUser FROM UserData u WHERE u.username = :username");
        query.setParameter("username", username);
        return (Boolean) query.getSingleResult();
    }

    //sprawdzanie zeby przyznac punkty
    public void setScoreOne(String username, double score)
    {
        EntityManager entityManager = PersistenceManager.getFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        if(check_User(username).equals(true))
        {
            Scores scores = entityManager.find(Scores.class, get_UID(username));
            entityTransaction.begin();
            scores.setScore(score);
            entityTransaction.commit();
            entityManager.close();
        }
    }


    //Pobieranie uid gracza
    public int get_UID(String username)
    {
        EntityManager entityManager = PersistenceManager.getFactory().createEntityManager();
        Query query = entityManager.createQuery("SELECT u.uid FROM UserData u WHERE u.username = :username");
        query.setParameter("username", username);
        return (int) query.getSingleResult();
    }

    //Ustawianie korzystania na true
    public void set_Usage_true(String username)
    {
        EntityManager entityManager = PersistenceManager.getFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        //Nie zadzaiala bo nie jest supportowane przez JPA xd

        UserData userData = entityManager.find(UserData.class, get_UID(username));
        entityTransaction.begin();
        userData.setUser(true);
        entityTransaction.commit();
        entityManager.close();
    }

    //na false
    public void set_Usage_false(String username)
    {
        EntityManager entityManager = PersistenceManager.getFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        //Nie zadzaiala bo nie jest supportowane przez JPA xd

        UserData userData = entityManager.find(UserData.class, get_UID(username));
        entityTransaction.begin();
        userData.setUser(false);
        entityTransaction.commit();
        entityManager.close();
    }

}
