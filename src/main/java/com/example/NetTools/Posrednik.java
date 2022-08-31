package com.example.NetTools;

import com.example.Meat.Demo.Board;

public class Posrednik
{
    public static Board board;

    public Posrednik(Board board)
    {
        this.board = board;
    }

    public Board giveBoard()
    {
        return this.board;
    }
}
