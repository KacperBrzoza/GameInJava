package PersistenceManager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PersistenceManager
{
    //private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("MyUnit");
    private static final EntityManagerFactory FACTORY =
            Persistence.createEntityManagerFactory("MyUnit");

    public static EntityManagerFactory getFactory() {
        return FACTORY;
    }

}
