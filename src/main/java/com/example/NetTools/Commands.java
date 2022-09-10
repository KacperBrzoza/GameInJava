package com.example.NetTools;

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
            return "-1";
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
            return "-1";
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
            return "-1";
        }
        return "-1";
    }



}
