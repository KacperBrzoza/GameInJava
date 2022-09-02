package com.example.Main.Game;

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
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class GameController
{

    private static boolean player_turn;
    @FXML
    Button EndTurnButton;
    @FXML
    Label MoneyPlayerValue;

    @FXML
    Label InfoLabel, CardCounter;

    @FXML
    GridPane BattleGrid;

    @FXML
    Pane mygrid0, mygrid1, mygrid3, mygrid5, mygrid6;
    @FXML
    private Button ExitButton;
    @FXML
    Pane SelectField1,SelectField2,SelectField3,SelectField4;

    @FXML
    Button TakeCardDeck, RageCardDeck, MoneyStack, LostCardDeck;

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
    @FXML
    protected void hiderInformation()
    {
        InfoLabel.setText("");
        InfoLabel.setStyle("-fx-font-size: 14pt;");
    }
    @FXML
    protected void onMoneyScreenEntered()
    {
        InfoLabel.setText("Ilość monet: " + MoneyPlayerValue.getText());
        InfoLabel.setStyle("-fx-font-size: 35pt;");
    }

    @FXML
    protected void onTakeCardClicked()
    {

    }
    @FXML
    protected void onTakeCardEntered()
    {
        InfoLabel.setText("Stos kart postaci - ...Kacper dopisz bo ja nie wiem jak to wyjasnic xd");
    }
    @FXML
    protected void onRageCardClicked()
    {

    }
    @FXML
    protected void onRageCardEntered()
    {
        InfoLabel.setText("Stos kart RAGE - ...Kacper dopisz bo ja nie wiem jak to wyjasnic xd");
    }
    @FXML
    protected void onMoneyStackClicked()
    {

    }
    @FXML
    protected void onMoneyStackEntered()
    {
        InfoLabel.setText("Stos monet - ...Kacper dopisz bo ja nie wiem jak to wyjasnic xd");
    }
    @FXML
    protected void onLostCardClicked()
    {

    }
    @FXML
    protected void onLostCardEntered()
    {
        InfoLabel.setText("Stos kart odrzuconych - ...Kacper dopisz bo ja nie wiem jak to wyjasnic xd");
    }

    @FXML
    protected void onMyRage0Entered()
    {
        InfoLabel.setText("Obecnie nie masz żadnej karty RAGE");
    }
    @FXML
    protected void onMyRage1Entered()
    {
        InfoLabel.setText("Obecnie nie masz żadnej karty RAGE");
    }
    @FXML
    protected void onMyRage2Entered()
    {
        InfoLabel.setText("Obecnie nie masz żadnej karty RAGE");
    }
    @FXML
    protected void onEnemyRage0Entered()
    {
        InfoLabel.setText("Przeciwnik obecnie nie ma żadnej karty RAGE");
    }
    @FXML
    protected void onEnemyRage1Entered()
    {
        InfoLabel.setText("Przeciwnik obecnie nie ma żadnej karty RAGE");
    }
    @FXML
    protected void onEnemyRage2Entered()
    {
        InfoLabel.setText("Przeciwnik obecnie nie ma żadnej karty RAGE");
    }
    @FXML
    protected void onMyCharacterEntered()
    {
        InfoLabel.setText("Gracz: d4krzyk \n Życia: 3");//Trzeba dodać nicki na wyswietlanie
        InfoLabel.setStyle("-fx-font-size: 24pt;");
    }
    @FXML
    protected void onEnemyCharacterEntered()
    {
        InfoLabel.setText("Przeciwnik: Brewek \n Życia: 3");//Trzeba dodać nicki na wyswietlanie
        InfoLabel.setStyle("-fx-font-size: 24pt;");
    }

    @FXML
    protected void onEndTurnButtonClicked()
    {
        EndTurnButton.setText("Tura Przeciwnika");
        EndTurnButton.setStyle("-fx-font-size: 22pt;");
        //-fx-font-size: 28pt; normalny rozmiar czcionki dla przycisku konca tury jesli tura wroci do gracza
        EndTurnButton.setDisable(true);
        TakeCardDeck.setDisable(true);
        RageCardDeck.setDisable(true);
        MoneyStack.setDisable(true);
        LostCardDeck.setDisable(true);;
        CardCounter.setDisable(true);

    }
    @FXML
    protected void onEndTurnButtonEntered()
    {
        InfoLabel.setText("Chcesz zakończyć turę?");
        InfoLabel.setStyle("-fx-font-size: 25pt;");
    }
    @FXML
    protected void onSelectField1Entered()
    {
        InfoLabel.setText("Pole 1");
    }
    @FXML
    protected void onSelectField2Entered()
    {
        InfoLabel.setText("Pole 2");
    }
    @FXML
    protected void onSelectField3Entered()
    {
        InfoLabel.setText("Pole 3");
    }
    @FXML
    protected void onSelectField4Entered()
    {
        InfoLabel.setText("Pole 4");
    }
    @FXML
    protected void onSelectField1Clicked()
    {

    }
    @FXML
    protected void onSelectField2Clicked()
    {

    }
    @FXML
    protected void onSelectField3Clicked()
    {

    }
    @FXML
    protected void onSelectField4Clicked()
    {

    }
    @FXML
    protected void onLeftPageButtonClicked()
    {
        /*Trzeba tu ogarnac programik na przesuwanie tych kafelek z ekwipunku bo jest problem i stylem
        tak latwo chyba to sie nie naprawi w sensie SelectField1.setstyle("-fx-background-image: sciezkapliku") srednio zadziala jak patrzac w przod
         */
    }
    @FXML
    protected void onRightPageButtonClicked()
    {

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

//Komentuje na razie bo podlaczam przyciski i chce czyste miec terminale mozna potem usunac komentarz
    /*protected String getCreatureName()
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
    }*/
    @FXML
    public void onMyGrid0Entered()
    {
        InfoLabel.setText("Moje pole 1");
    }
    @FXML
    public void onMyGrid1Entered()
    {
        InfoLabel.setText("Moje pole 2");
    }
    @FXML
    public void onMyGrid2Entered()
    {
        InfoLabel.setText("Moje pole 3");
    }
    @FXML
    public void onMyGrid3Entered()
    {
        InfoLabel.setText("Moje pole 4");
    }
    @FXML
    public void onMyGrid4Entered()
    {
        InfoLabel.setText("Moje pole 5");
    }
    @FXML
    public void onEnemyGrid0Entered()
    {
        InfoLabel.setText("Pole przeciwnika 1");
    }
    @FXML
    public void onEnemyGrid1Entered()
    {
        InfoLabel.setText("Pole przeciwnika 2");
    }
    @FXML
    public void onEnemyGrid2Entered()
    {
        InfoLabel.setText("Pole przeciwnika 3");
    }
    @FXML
    public void onEnemyGrid3Entered()
    {
        InfoLabel.setText("Pole przeciwnika 4");
    }
    @FXML
    public void onEnemyGrid4Entered()
    {
        InfoLabel.setText("Pole przeciwnika 5");
    }


    @FXML
    public void onMouseEnteredButton()
    {
        //getCreatureName(mygrid0);
        gameData = new GameData();
        //gameData.setPane(mygrid0);
        //InfoLabel.setText(getCreatureName());
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
}
