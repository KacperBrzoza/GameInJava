package com.example.Main.PersistenceManager;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PersistenceManager
{
    private static final EntityManagerFactory FACTORY =
            Persistence.createEntityManagerFactory("MyUnit");

    public static EntityManagerFactory getFactory() {
        return FACTORY;
    }

}
