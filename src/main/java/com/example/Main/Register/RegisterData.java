package com.example.Main.Register;

public class RegisterData
{
    private String login;
    private String password;

    public RegisterData(String login, String password)
    {
        this.login = login;
        this.password = password;
    }


    public RegisterData()
    {
    }

    public String getLogin()
    {
        return login;
    }

    public void setLogin(String login)
    {
        this.login = login;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    @Override
    public String toString()
    {
        return "RegisterData{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
