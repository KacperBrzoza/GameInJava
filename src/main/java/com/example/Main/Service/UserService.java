package com.example.Main.Service;

import com.example.Main.Model.IpTable;
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
    //public ObservableList<UserData> observableList;

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

    //Testowo do IP
    /*
    public void add_IP(String IP)
    {
        IpTable ipTable= new IpTable();
        ipTable.setIpAddress(IP);
        EntityManager entityManager = PersistenceManager.getFactory().createEntityManager();
        EntityTransaction transaction =entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(ipTable);
        transaction.commit();
    }
     */

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
            //delete_IP();
            System.out.println("Jestem switcherem 2");
        }
    }

    public void delete_IP()
    {
        /*
        IpTable ipTable;
        EntityManager entityManager = PersistenceManager.getFactory().createEntityManager();
        Query query = entityManager.createQuery("DELETE FROM IpTable i WHERE i.ipid = 1");

         */
        EntityManager entityManager = PersistenceManager.getFactory().createEntityManager();
        IpTable ipTable = entityManager.find(IpTable.class, 1);
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.remove(ipTable);
        transaction.commit();
    }

    public String get_Ip() //List<IpTable>
    {
        //IpTable ipTable = new IpTable();
        EntityManager entityManager = PersistenceManager.getFactory().createEntityManager();
        Query query = entityManager.createQuery("SELECT i.ipAddress FROM IpTable i WHERE i.ipid = 1");
        return query.getResultList().toString().replace("[", "").replace("]", "").replace(" ","");
    }

    //Funkcja potrzebna do wyswietla nickow graczy - Nie dziala wyswietlanie w rankingu
    //TODO
    //Potrzeba dodania jakos scoresow z drugiej tabeli do tableview
    //Zamysl - Zapytanie przez Inner Joina i wyciagniecie nickow + score  - najoptymalniejsze
    //SELECT u.username, s.score FROM user_data u INNER JOIN scores s ON u.uid = s.uid; -
    public ObservableList<UserData> getAll()
    {
        ObservableList<UserData> observableList = FXCollections.observableArrayList(); //Wszystko na string
        EntityManager entityManager = PersistenceManager.getFactory().createEntityManager();
        List<UserData> uList = entityManager.createQuery("select u FROM UserData u", UserData.class).getResultList();
        observableList.addAll(uList);
        return observableList;
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
