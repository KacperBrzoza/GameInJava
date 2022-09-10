package com.example.Main.Model;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "user_data", schema = "public")
public class UserData
{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    //@Column(name = "uid")
    @NotNull
    private Long uid;
    @Basic
    @Column(length = 30) //name = "username" ,
    @NotNull
    private String username;
    @Basic
    @Column(length = 20) //name = "password" ,
    @NotNull
    private String password;

    /*
    @OneToOne
    @JoinColumn(name = "scores_uid")
    private Scores scores;


    public Scores getScores()
    {
        return scores;
    }

    public void setScores(Scores scores)
    {
        this.scores = scores;
    }
     */

    public Long getUid()
    {
        return uid;
    }

    public void setUid(Long uid)
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

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserData userData = (UserData) o;
        return uid == userData.uid && Objects.equals(username, userData.username) && Objects.equals(password, userData.password);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(uid, username, password);
    }
}
