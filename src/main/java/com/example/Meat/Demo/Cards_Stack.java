package com.example.Meat.Demo;

import com.example.Main.Game.GameController;
import com.example.Meat.Creatures.*;

import javax.swing.plaf.PanelUI;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.System.exit;

//stos kart stworów
public class Cards_Stack {

    private final List<Creature> creatures;

    //inicjalizacja "stosu" kart stworów, to tu będą ręcznie wprowadzone wszystkie karty
    public Cards_Stack(){
        creatures = new ArrayList<>();
        //stwory bez mocy           src/main/resources/img/Creatures/XXX.png
        creatures.add(new Creature(0, 4, 2, "src/main/resources/img/Creatures/042.png"));
        creatures.add(new Creature(0, 2, 3, "src/main/resources/img/Creatures/023.png"));
        for(int i = 0; i < 3; i++)
            creatures.add(new Creature(1, 2, 2, "src/main/resources/img/Creatures/122.png"));
        creatures.add(new Creature(2, 2, 2, "src/main/resources/img/Creatures/222.png"));
        creatures.add(new Creature(2, 1, 3, "src/main/resources/img/Creatures/213.png"));
        creatures.add(new Creature(2, 3, 1, "src/main/resources/img/Creatures/231.png"));
        creatures.add(new Creature(3, 4, 1, "src/main/resources/img/Creatures/341.png"));
        for(int i = 0; i < 5; i++)
            creatures.add(new Creature(3, 3, 2, "src/main/resources/img/Creatures/332.png"));
        for(int i = 0; i < 3; i++)
            creatures.add(new Creature(4, 2, 4, "src/main/resources/img/Creatures/424.png"));
        creatures.add(new Creature(4, 5, 1, "src/main/resources/img/Creatures/451.png"));
        for(int i = 0; i < 3; i++)
            creatures.add(new Creature(4, 4, 2, "src/main/resources/img/Creatures/442.png"));
        for(int i = 0; i < 3; i++)
            creatures.add(new Creature(4, 3, 3, "src/main/resources/img/Creatures/433.png"));
        creatures.add(new Creature(5, 5, 2, "src/main/resources/img/Creatures/552.png"));
        for(int i = 0; i < 3; i++)
            creatures.add(new Creature(5, 4, 3, "src/main/resources/img/Creatures/543.png"));
        for(int i = 0; i < 3; i++)
            creatures.add(new Creature(5, 3, 4, "src/main/resources/img/Creatures/534.png"));

        creatures.add(new Creature(6, 5, 3, "src/main/resources/img/Creatures/653.png"));
        creatures.add(new Creature(6, 3, 5, "src/main/resources/img/Creatures/635.png"));
        creatures.add(new Creature(6, 4, 4, "src/main/resources/img/Creatures/644.png"));
        creatures.add(new Creature(6, 6, 2, "src/main/resources/img/Creatures/662.png"));
        creatures.add(new Creature(7, 4, 5, "src/main/resources/img/Creatures/745.png"));
        creatures.add(new Creature(7, 1, 8, "src/main/resources/img/Creatures/718.png"));
        creatures.add(new Creature(7, 6, 3, "src/main/resources/img/Creatures/763.png"));
        creatures.add(new Creature(7, 3, 6, "src/main/resources/img/Creatures/736.png"));
        creatures.add(new Creature(7, 5, 4, "src/main/resources/img/Creatures/754.png"));
        creatures.add(new Creature(8, 5, 5, "src/main/resources/img/Creatures/855.png"));
        creatures.add(new Creature(8, 8, 2, "src/main/resources/img/Creatures/882.png"));
        creatures.add(new Creature(8, 3, 7, "src/main/resources/img/Creatures/837.png"));
        creatures.add(new Creature(8, 7, 3, "src/main/resources/img/Creatures/873.png"));
        creatures.add(new Creature(8, 4, 6, "src/main/resources/img/Creatures/846.png"));
        creatures.add(new Creature(8, 6, 4, "src/main/resources/img/Creatures/864.png"));
        creatures.add(new Creature(9, 5, 6, "src/main/resources/img/Creatures/956.png"));
        creatures.add(new Creature(9, 9, 2, "src/main/resources/img/Creatures/992.png"));
        creatures.add(new Creature(9, 6, 5, "src/main/resources/img/Creatures/965.png"));
        creatures.add(new Creature(9, 8, 3, "src/main/resources/img/Creatures/983.png"));
        creatures.add(new Creature(9, 4, 7, "src/main/resources/img/Creatures/947.png"));
        creatures.add(new Creature(9, 7, 4, "src/main/resources/img/Creatures/974.png"));
        creatures.add(new Creature(10, 5, 7, "src/main/resources/img/Creatures/1057.png"));
        creatures.add(new Creature(10, 7, 5, "src/main/resources/img/Creatures/1075.png"));
        creatures.add(new Creature(10, 4, 8, "src/main/resources/img/Creatures/1048.png"));
        creatures.add(new Creature(10, 8, 4, "src/main/resources/img/Creatures/1084.png"));
        creatures.add(new Creature(10, 6, 6, "src/main/resources/img/Creatures/1066.png"));
        creatures.add(new Creature(11, 7, 6, "src/main/resources/img/Creatures/1176.png"));
        creatures.add(new Creature(11, 6, 7, "src/main/resources/img/Creatures/1167.png"));
        creatures.add(new Creature(12, 7, 7, "src/main/resources/img/Creatures/1277.png"));

        //z mocą D
        for(int i = 0; i < 4; i++)
            creatures.add(new D_Creature(4, 2, 3, "src/main/resources/img/Creatures/423D.png"));
        for(int i = 0; i < 3; i++)
            creatures.add(new D_Creature(6, 3, 4, "src/main/resources/img/Creatures/634D.png"));
        for(int i = 0; i < 2; i++)
            creatures.add(new D_Creature(7, 5, 3, "src/main/resources/img/Creatures/753D.png"));
        creatures.add(new D_Creature(8, 5, 4, "src/main/resources/img/Creatures/854D.png"));
        creatures.add(new D_Creature(8, 7, 2, "src/main/resources/img/Creatures/872D.png"));

        //z mocą E
        for(int i = 0; i < 10; i++)
            creatures.add(new E_Creature(0, 2, 2, "src/main/resources/img/Creatures/022E.png"));
        for(int i = 0; i < 3; i++)
            creatures.add(new E_Creature(4, 4, 1, "src/main/resources/img/Creatures/441E.png"));
        for(int i = 0; i < 5; i++)
            creatures.add(new E_Creature(4, 3, 2, "src/main/resources/img/Creatures/432E.png"));
        for(int i = 0; i < 2; i++)
            creatures.add(new E_Creature(5, 3, 3, "src/main/resources/img/Creatures/533E.png"));
        for(int i = 0; i < 2; i++)
            creatures.add(new E_Creature(5, 4, 2, "src/main/resources/img/Creatures/542E.png"));
        for(int i = 0; i < 5; i++)
            creatures.add(new E_Creature(6, 4, 3, "src/main/resources/img/Creatures/643E.png"));
        creatures.add(new E_Creature(0, 5, 4, "src/main/resources/img/Creatures/054E.png"));
        creatures.add(new E_Creature(10, 9, 2, "src/main/resources/img/Creatures/11102E.png"));

        //z mocą F
        for(int i = 0; i < 3; i++)
            creatures.add(new F_Creature(4, 3, 2, "src/main/resources/img/Creatures/432F.png"));
        for(int i = 0; i < 3; i++)
            creatures.add(new F_Creature(5, 4, 2, "src/main/resources/img/Creatures/542F.png"));
        creatures.add(new F_Creature(7, 6, 2, "src/main/resources/img/Creatures/762F.png"));
        creatures.add(new F_Creature(9, 8, 2, "src/main/resources/img/Creatures/982F.png"));
        creatures.add(new F_Creature(10, 5, 6, "src/main/resources/img/Creatures/1056F.png"));

        //z mocą G
        for(int i = 0; i < 3; i++)
            creatures.add(new G_Creature(3, 1, 3, "src/main/resources/img/Creatures/313G.png"));
        for(int i = 0; i < 2; i++)
            creatures.add(new G_Creature(5, 3, 3, "src/main/resources/img/Creatures/533G.png"));
        creatures.add(new G_Creature(6, 3, 4, "src/main/resources/img/Creatures/634G.png"));
        creatures.add(new G_Creature(6, 5, 2, "src/main/resources/img/Creatures/652G.png"));
        creatures.add(new G_Creature(7, 3, 5, "src/main/resources/img/Creatures/745G.png"));
        creatures.add(new G_Creature(8, 2, 7, "src/main/resources/img/Creatures/827G.png"));
        
        //z mocą H
        for(int i = 0; i < 2; i++)
            creatures.add(new H_Creature(3, 1, 3, "src/main/resources/img/Creatures/313H.png"));
        for(int i = 0; i < 3; i++)
            creatures.add(new H_Creature(3, 2, 2, "src/main/resources/img/Creatures/322H.png"));
        for(int i = 0; i < 2; i++)
            creatures.add(new H_Creature(5, 2, 4, "src/main/resources/img/Creatures/524H.png"));
        creatures.add(new H_Creature(6, 2, 5, "src/main/resources/img/Creatures/625H.png"));
        creatures.add(new H_Creature(7, 2, 6, "src/main/resources/img/Creatures/726H.png"));
        creatures.add(new H_Creature(7, 4, 4, "src/main/resources/img/Creatures/744H.png"));
        creatures.add(new H_Creature(9, 2, 8, "src/main/resources/img/Creatures/928H.png"));

        //z mocą J - chwilowo wyłączona z użytku
        /*
        for(int i = 0; i < 3; i++)
            creatures.add(new J_Creature(1, 1, 1, "src/main/resources/img/Creatures/J.png"));
        for(int i = 0; i < 2; i++)
            creatures.add(new J_Creature(3, 2, 2, "src/main/resources/img/Creatures/J.png"));
        for(int i = 0; i < 2; i++)
            creatures.add(new J_Creature(3, 1, 3, "src/main/resources/img/Creatures/J.png"));
        creatures.add(new J_Creature(6, 4, 3, "src/main/resources/img/Creatures/J.png"));
        creatures.add(new J_Creature(7, 3, 5, "src/main/resources/img/Creatures/J.png"));
         */


        //z mocą M
        for(int i = 0; i < 4; i++)
            creatures.add(new M_Creature(4, 2, 3, "src/main/resources/img/Creatures/423M.png"));
        for(int i = 0; i < 3; i++)
            creatures.add(new M_Creature(5, 3, 3, "src/main/resources/img/Creatures/533M.png"));
        for(int i = 0; i < 2; i++)
            creatures.add(new M_Creature(6, 2, 5, "src/main/resources/img/Creatures/625M.png"));
        creatures.add(new M_Creature(5, 2, 4, "src/main/resources/img/Creatures/524M.png"));
        creatures.add(new M_Creature(8, 5, 4, "src/main/resources/img/Creatures/854M.png"));
        
        //z mocą N
        for(int i = 0; i < 3; i++)
            creatures.add(new N_Creature(3, 2, 2, "src/main/resources/img/Creatures/322N.png"));
        for(int i = 0; i < 3; i++)
            creatures.add(new N_Creature(4, 3, 2, "src/main/resources/img/Creatures/432N.png"));
        for(int i = 0; i < 2; i++)
            creatures.add(new N_Creature(5, 4, 2, "src/main/resources/img/Creatures/542N.png"));
        creatures.add(new N_Creature(8, 3, 6, "src/main/resources/img/Creatures/836N.png"));
        creatures.add(new N_Creature(8, 4, 5, "src/main/resources/img/Creatures/845N.png"));

        //z mocą O
        for(int i = 0; i < 3; i++)
            creatures.add(new O_Creature(3, 1, 1, "src/main/resources/img/Creatures/311O.png"));
        for(int i = 0; i < 2; i++)
            creatures.add(new O_Creature(4, 1, 2, "src/main/resources/img/Creatures/412O.png"));
        creatures.add(new O_Creature(8, 3, 4, "src/main/resources/img/Creatures/834O.png"));
        creatures.add(new O_Creature(11, 5, 5, "src/main/resources/img/Creatures/1155O.png"));

        //z mocą R
        for(int i = 0; i < 3; i++)
            creatures.add(new R_Creature(1, 1, 1, "src/main/resources/img/Creatures/111R.png"));
        for(int i = 0; i < 2; i++)
            creatures.add(new R_Creature(2, 1, 2, "src/main/resources/img/Creatures/212R.png"));
        creatures.add(new R_Creature(4, 3, 2, "src/main/resources/img/Creatures/432R.png"));
        creatures.add(new R_Creature(5, 3, 3, "src/main/resources/img/Creatures/533R.png"));
        creatures.add(new R_Creature(7, 4, 4, "src/main/resources/img/Creatures/744R.png"));
        
        //z mocą U
        for(int i = 0; i < 3; i++)
            creatures.add(new U_Creature(4, 1, 4, "src/main/resources/img/Creatures/414U.png"));
        for(int i = 0; i < 3; i++)
            creatures.add(new U_Creature(6, 3, 4, "src/main/resources/img/Creatures/634U.png"));
        for(int i = 0; i < 2; i++)
            creatures.add(new U_Creature(6, 2, 5, "src/main/resources/img/Creatures/625U.png"));
        creatures.add(new U_Creature(6, 1, 6, "src/main/resources/img/Creatures/616U.png"));
        creatures.add(new U_Creature(8, 3, 6, "src/main/resources/img/Creatures/836U.png"));
        creatures.add(new U_Creature(9, 2, 8, "src/main/resources/img/Creatures/928U.png"));
        creatures.add(new U_Creature(9, 1, 9, "src/main/resources/img/Creatures/919U.png"));

        //z mocą X
        for(int i = 0; i < 3; i++)
            creatures.add(new X_Creature(4, 2, 2, "src/main/resources/img/Creatures/422X.png"));
        creatures.add(new X_Creature(7, 4, 3, "src/main/resources/img/Creatures/743X.png"));
        creatures.add(new X_Creature(8, 5, 3, "src/main/resources/img/Creatures/853X.png"));
        creatures.add(new X_Creature(9, 5, 4, "src/main/resources/img/Creatures/954X.png"));
        creatures.add(new X_Creature(10, 8, 2, "src/main/resources/img/Creatures/1082X.png"));
        creatures.add(new X_Creature(14, 9, 5, "src/main/resources/img/Creatures/1495X.png"));
        
        //z mocą Z
        for(int i = 0; i < 3; i++)
            creatures.add(new Z_Creature(4, 2, 3, "src/main/resources/img/Creatures/423Z.png"));
        creatures.add(new Z_Creature(7, 3, 5, "src/main/resources/img/Creatures/735Z.png"));
        creatures.add(new Z_Creature(9, 4, 6, "src/main/resources/img/Creatures/946Z.png"));
        creatures.add(new Z_Creature(10, 6, 5, "src/main/resources/img/Creatures/1065Z.png"));
    }

    //poniższa metoda losuje kartę z tego "stosu" i ją zwraca
    //ma służyć do dobierania kart przez graczy
    public Creature giveCard(BufferedWriter out, Player you, Player opponent, GameController gameController){
        Random rand = new Random();
        int n = rand.nextInt(creatures.size());
        Creature card = creatures.get(n);
        creatures.remove(n);
        //System.out.println("Na stosie zostalo " + creatures.size() + " kart");
        GameController.newNumberValue(gameController.CardCounter, "" + creatures.size());                   //podmiana liczby kart na stosie dla serwera
        GameController.server.sendMessageToClient("NEW_CARDS_STACK_SIZE_" + creatures.size());  //podmiana liczby kart na stosie dla clienta
        if(creatures.size() == 0)
            endGame(out, you, opponent);
        return card;
    }

    public int size(){
        return creatures.size();
    }

    //metoda przerywa grę gdy skończą się karty na stosie (do testów)
    private void endGame(BufferedWriter out, Player you, Player opponent){
        System.out.println("\n" + "\n" + "\n" + "\n" + "\n"  + "\n" + "\n" + "\n" + "\n" + "\n");
        System.out.println("GRA SKONCZONA!");
        System.out.println("Gracz 1 " + you.score + ":" + opponent.score + " Gracz 2");
        //out.println("\n" + "\n" + "\n" + "\n" + "\n"  + "\n" + "\n" + "\n" + "\n" + "\n");
        //out.println("GRA SKONCZONA!");
        //out.println("Gracz 1 " + you.score + ":" + opponent.score + " Gracz 2");
        //out.println("END_GAME");
        /*
        TU NALEŻY WSTAWIĆ FRAGEMNT KODU WYSYŁAJĄCY WYNIKI DO BAZY
        MOŻE NP ŚCIĄGNĄĆ TYCH GRACZY Z TABELI WYNIKI, DODAĆ IM PUNKTY Z TEJ GRY I ZUPDATOWAĆ REKORDY W BAZIE
         */
        exit(0);
    }
}
