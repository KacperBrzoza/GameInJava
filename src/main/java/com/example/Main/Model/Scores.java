package com.example.Main.Model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Scores
{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    //@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    //todo
    //Dodac zaleznosci do encji (OnetoOne) oraz okreslic ich typy
    //@Column(name = "uid")
    private int uid;
    @Basic
    //@Column(name = "score")
    private Integer score;

    public int getUid()
    {
        return uid;
    }

    public void setUid(int uid)
    {
        this.uid = uid;
    }

    public Integer getScore()
    {
        return score;
    }

    public void setScore(Integer score)
    {
        this.score = score;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Scores scores = (Scores) o;
        return uid == scores.uid && Objects.equals(score, scores.score);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(uid, score);
    }
}
