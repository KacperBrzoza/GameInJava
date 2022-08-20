package com.example.Main;

import com.example.Main.Database.TestService;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class TestHibernate
{
    private static final EntityManagerFactory FACTORY =
            Persistence.createEntityManagerFactory("MyUnit");

    public static void main(String[] args)
    {
        TestService testService = new TestService();
        testService.findOne(1);
    }
    //Do wykonywania zapytan, etc.
    public static EntityManagerFactory getFactory()
    {
        return FACTORY;
    }
}
