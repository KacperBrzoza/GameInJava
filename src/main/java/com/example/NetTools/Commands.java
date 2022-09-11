package com.example.NetTools;

import com.example.Main.Game.GameController;
import javafx.scene.image.Image;

import java.io.File;

public class Commands {

    public static boolean endGame(String in)
    {
        return in.equals("LAST_MESSAGE");
    }

    public static boolean yourTurn(String in){
        return in.equals("YOUR_TURN");
    }
    public static boolean unableRightShowBut(String in){
        return in.equals("UNABLE_RIGHT_SHOW_BUT");
    }

    //SHOW_EQ
    public static boolean showEQ(String in){
        return in.equals("SHOW_EQ");
    }
    public static boolean showBattleField(String in){
        return in.equals("SHOW_BATTLE_FIELD");
    }

    //NEW_CARDS_STACK_SIZE_XYZ
    public static String newCardStackSize(String in){
        if(in.length() >= 22 && in.length() <= 24){
            char [] command = in.toCharArray();
            String test = "";
            int i = 0;
            while (i < 21){
                test += command[i];
                i++;
            }
            if(test.equals("NEW_CARDS_STACK_SIZE_")){
                test = "";
                while (i < in.length()){
                    test += command[i];
                    i++;
                }
                return test;
            }
        }
        return "-1";
    }

    //NEW_MY_MONEY_VAL_XYZ
    public static String newMyMoneyVal(String in) {
        if (in.length() >= 18 && in.length() <= 20) {
            char [] command = in.toCharArray();
            String test = "";
            int i = 0;
            while (i < 17){
                test += command[i];
                i++;
            }
            if(test.equals("NEW_MY_MONEY_VAL_")){
                test = "";
                while (i < in.length()){
                    test += command[i];
                    i++;
                }
                return test;
            }
        }
        return "-1";
    }

    //PATH_-------------------
    public static String path(String in){
        if(in.length() > 5){
            char [] command = in.toCharArray();
            String test = "";
            int i = 0;
            while (i < 5){
                test += command[i];
                i++;
            }
            if(test.equals("PATH_")){
                test = "";
                while (i < in.length()){
                    test += command[i];
                    i++;
                }
                return test;
            }
        }
        return "-1";
    }

    //SET_FIELD_X_--------------------
    //X - [0;9]
    public static String setField(String in, GameController gameController){
        if(in.length() > 12){
            char [] command = in.toCharArray();
            String test = "";
            int i = 0;
            while (i < 10){
                test += command[i];
                i++;
            }
            if(test.equals("SET_FIELD_")){
                test = "";
                int position;
                position = command[i] - 48;
                i+=2;
                while (i < in.length()){
                    test += command[i];
                    i++;
                }
                Image image;
                if(test.equals("N")){
                    image = null;
                }
                else {
                    File file = new File(test);
                    image = new Image(file.toURI().toString());
                }
                GameController.setSingleField(gameController.fields, image, position);
                return "1";
            }
        }
        return "-1";
    }



}
