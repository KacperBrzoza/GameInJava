package com.example.Main.Game;

import com.example.Meat.Creatures.Creature;
import com.example.Meat.Demo.Board;
import com.example.NetTools.Posrednik;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class GameController
{

    private static boolean player_turn;

    @FXML
    Label MoneyPlayerValue;

    @FXML
    Label InfoLabel;

    @FXML
    GridPane BattleGrid;

    @FXML
    Pane mygrid0, mygrid1, mygrid3, mygrid5, mygrid6;
    @FXML
    private Button ExitButton;

    GameData gameData;
    Posrednik posrednik;
    //public Board board = posrednik.giveBoard();

    //String path = "src/main/resources/music/the_witcher.mp3";
    //Media media = new Media(new File(path).toURI().toString());
    //MediaPlayer mediaPlayer = new MediaPlayer(media);


    @FXML
    protected void onExitButtonClicked(ActionEvent event) throws IOException
    {
        URL url = new File("src/main/resources/com/example/Main/Menu/Menu-view.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public static void cos()
    {
        System.err.println("oho, dzialam");
    }

    @FXML
    public void openGame(ActionEvent event) throws IOException //static
    {
        URL url = new File("src/main/resources/com/example/Main/Menu/Menu-view.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        /*
        MediaPlayer a = new MediaPlayer(new Media(resource.toString()));
        a.setOnEndOfMedia(new Runnable()
        {
            public void run()
            {
                a.seek(Duration.ZERO);
            }
        });
        a.play();
         */

    }


    protected String getCreatureName()
    {
        int player = 1;
        int id = 0;

        Creature creature;
        Board board = null;

        if (gameData.getPane().equals(mygrid0))
        {
            player = 1;
            id = 0;
        }
        else if(gameData.getPane().equals(mygrid1))
        {
            player = 1;
            id = 1;
        }
        creature = board.getCreature(player, id);
        String creatur = creature.toString();
        return creatur;
    }


    @FXML
    public void onMouseEnteredButton()
    {
        //getCreatureName(mygrid0);
        gameData = new GameData();
        gameData.setPane(mygrid0);
        InfoLabel.setText(getCreatureName());
    }

    @FXML
    public void onMouseEnteredButton1()
    {
        InfoLabel.setText("Blabla");
    }

    @FXML
    public void onMouseEnteredButton2()
    {
        InfoLabel.setText("ROFL");
    }

    @FXML
    public void onMouseEnteredButton3()
    {
        InfoLabel.setText("Lmao");
    }

    @FXML
    public void onMouseEnteredButton4()
    {
        InfoLabel.setText("Konopie");
    }

    @FXML
    public void onMouseExitButton()
    {
        InfoLabel.setText("");
    }
}
