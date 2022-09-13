package com.example.NetTools;

import com.example.Main.Game.GameController;
import javafx.scene.image.Image;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.File;

public class Commands {

    public static boolean breakGame(String in)
    {
        return in.equals("LAST_MESSAGE");
    }
    public static boolean endGame(String in)
    {
        return in.equals("END_GAME");
    }

    //PLAYER_XXX_POINTS_Y.Z
    //PLAYER_ONE_POINTS_0.5
    public static boolean playerPoints(String in){
        if(in.length() == 21){
            char [] command = in.toCharArray();
            String test = "";
            int i = 0;
            while (i < 8){
                test += command[i];
                i++;
            }
            if(test.equals("PLAYER_")){
                test = "";
                while (i < 11){
                    test += command[i];
                    i++;
                }
                while (i < 18){
                    i++;
                }
                if(test.equals("ONE")){
                    test = "";
                    while (i < 21){
                        test += command[i];
                        i++;
                    }
                    float number = Float.parseFloat(test);
                    if(number == 0.5)
                        GameController.PLAYER_ONE_POINTS -= number;
                    else
                        GameController.PLAYER_ONE_POINTS += number;
                }
                else{
                    test = "";
                    while (i < 21){
                        test += command[i];
                        i++;
                    }
                    float number = Float.parseFloat(test);
                    if(number == 0.5)
                        GameController.PLAYER_TWO_POINTS -= number;
                    else
                        GameController.PLAYER_TWO_POINTS += number;
                }
                return true;
            }
        }
        return false;
    }

    public static boolean yourTurn(String in){
        return in.equals("YOUR_TURN");
    }
    public static boolean unableRightShowBut(String in){
        return in.equals("UNABLE_RIGHT_SHOW_BUT");
    }
    public static boolean selectingPhase(String in){return in.equals("SELECTING_PHASE");}

    //SHOW_EQ
    public static boolean showEQ(String in){
        return in.equals("SHOW_EQ");
    }

    //REMOVE_FROM_EQ
    public static boolean removeFromEQ(String in){return  in.equals("REMOVE_FROM_EQ");}
    public static boolean expensive(String in){return  in.equals("EXPENSIVE");}
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

    //DISCARD_---------
    public static String discardImage(String in){
        if(in.length() > 8){
            char [] command = in.toCharArray();
            String test = "";
            int i = 0;
            while (i < 8){
                test += command[i];
                i++;
            }
            if(test.equals("DISCARD_")){
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

    //PHASE_X
    public static int phase(String in){
        if(in.length() == 7){
            char [] command = in.toCharArray();
            String test = "";
            int i = 0;
            while (i < 6){
                test += command[i];
                i++;
            }
            if(test.equals("PHASE_")){
                int number;
                number = command[i] - 48;
                return number;
            }
        }
        return -1;
    }

    //CHOSE_X...
    public static int choice(String in){
        if(in.length() >= 7) {
            char[] command = in.toCharArray();
            String test = "";
            int i = 0;
            while (i < 7) {
                test += command[i];
                i++;
            }
            if(test.equals("CHOSE_")){
                if(command[i] == 45){
                    return -1;
                }
                else {
                    test = "";
                    while (i < in.length()){
                        test += command[i];
                        i++;
                    }
                    return Integer.parseInt(test);
                }
            }
        }
        return -100;
    }

    //LOSE_HP_------
    public static String loseHp(String in, GameController gameController){
        if(in.length() > 8){
            char [] command = in.toCharArray();
            String test = "";
            int i = 0;
            while (i < 8){
                test += command[i];
                i++;
            }
            if(test.equals("LOSE_HP_")){
                test = "";
                while (i < in.length()){
                    test += command[i];
                    i++;
                }
                //size size-1 size-2 size-3 size-4 size-5
                //       g      n      p      .     d / u
                i -= 5;
                if(command[i] == 'd'){
                    GameController.loseHp(gameController.EnemyCharacter, 2);
                }
                else {
                    GameController.loseHp(gameController.MyCharacter, 1);
                }

                return test;
            }
        }
        return "-1";
    }

}
