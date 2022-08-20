package com.example.Main.Database;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Test
{
    @Id
    @GeneratedValue
    private long id;
    private String imie;
    private String nazwisko;

    public Test(long id, String imie, String nazwisko)
    {
        this.id = id;
        this.imie = imie;
        this.nazwisko = nazwisko;
    }

    public Test()
    {
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getImie()
    {
        return imie;
    }

    public void setImie(String imie)
    {
        this.imie = imie;
    }

    public String getNazwisko()
    {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko)
    {
        this.nazwisko = nazwisko;
    }

    @Override
    public String toString()
    {
        return "Test{" +
                "id=" + id +
                ", imie='" + imie + '\'' +
                ", nazwisko='" + nazwisko + '\'' +
                '}';
    }
}
