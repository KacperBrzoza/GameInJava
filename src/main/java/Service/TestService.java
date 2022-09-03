package Service;

import PersistenceManager.PersistenceManager;
import Model.Test;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class TestService
{

    public List<Test> findAll()
    {
        EntityManager entityManager = PersistenceManager.getFactory().createEntityManager();
        Query query = entityManager.createQuery("SELECT c FROM Test c");
        return query.getResultList();
    }

    public TestService()
    {
    }
}
