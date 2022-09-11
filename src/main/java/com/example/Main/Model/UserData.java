package com.example.Main.Model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "user_data", schema = "public", catalog = "pvjfjkvx")
public class UserData
{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "uid")
    private int uid;
    @Basic
    @Column(name = "username")
    private String username;
    @Basic
    @Column(name = "password")
    private String password;
    @Basic
    @Column(name = "is_user")
    private boolean isUser;

    public int getUid()
    {
        return uid;
    }

    public void setUid(int uid)
    {
        this.uid = uid;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public boolean isUser()
    {
        return isUser;
    }

    public void setUser(boolean user)
    {
        isUser = user;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserData userData = (UserData) o;
        return uid == userData.uid && isUser == userData.isUser && Objects.equals(username, userData.username) && Objects.equals(password, userData.password);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(uid, username, password, isUser);
    }
}
