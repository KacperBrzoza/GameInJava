package com.example.Main.Register;

import java.util.regex.Pattern;

public class UserPassVal
{
    //Walidacja hasla
    public static boolean isValidPass(String password)
    {
        String passwordRegex = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*]).{8,20}$";

        Pattern pattern = Pattern.compile(passwordRegex);
        if(password == null)
        {
            return false;
        }
        return pattern.matcher(password).matches();
    }

    //Walidacja nazwy uzytkownika
    public static boolean isValidUsrnm(String username)
    {
        String userRegex = "^[a-zA-Z][a-zA-Z0-9_]{7,29}$";

        Pattern pattern = Pattern.compile(userRegex);

        if(username == null)
        {
            return false;
        }
        return pattern.matcher(username).matches();
    }
}
