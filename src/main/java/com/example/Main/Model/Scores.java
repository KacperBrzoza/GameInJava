package com.example.Main.Model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Scores
{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "uid")
    private int uid;
    @Basic
    @Column(name = "username")
    private String username;
    @Basic
    @Column(name = "score")
    private Double score;

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

    public Double getScore()
    {
        return score;
    }

    public void setScore(Double score)
    {
        this.score += score;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Scores scores = (Scores) o;
        return uid == scores.uid && Objects.equals(username, scores.username) && Objects.equals(score, scores.score);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(uid, username, score);
    }
}
