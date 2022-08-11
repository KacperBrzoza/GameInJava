package com.example.Controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

//Klasa do lączenia się z bazą danych
public class DatabaseConnection
{
    private Statement statement = null;
    private final Connection connection;

    //Otwieranie polaczenia z baza danych
    public DatabaseConnection() throws SQLException, ClassNotFoundException
    {
        Class.forName("org.postgresql.Driver");
        //Swoje dane musicie wrzucic tu jak chcecie utworzyc tabele i z niej korzystac
        connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","","");
        System.out.println("Opened database successfully");
    }

    //Tworzenie bazy
    public void create_base() throws SQLException
    {
        statement = connection.createStatement();
        String sql = "CREATE TABLE gameregister " +
                "(LOGIN TEXT NOT NULL PRIMARY KEY, " +
                "PASSWORD TEXT NOT NULL)";
        statement.executeUpdate(sql);
        statement.close();
        connection.close();
    }

    //Dodawanie uzytkownika
    public void add_user(String login, String password) throws SQLException
    {
        statement = connection.createStatement();
        String sql = "INSERT INTO gameregister VALUES ('"+login+"' , '"+password+"');";

        statement.executeUpdate(sql);
        statement.close();
    }

    //Trzeba jeszcze bedzie dodac weryfikacje,czy uzytkownik o takim loginie jest w bazie - proste
}
