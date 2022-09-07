package com.example.Main.Game;

import com.example.Meat.Demo.OnlineGame;
import com.example.NetTools.Client;
import com.example.NetTools.Server;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;
import java.util.ResourceBundle;

import static com.example.Main.Menu.WaitingEnemyController.SWITCHER;

public class GameController implements Initializable
{

    @FXML
    Button EndTurnButton;
    @FXML
    Label MoneyPlayerValue;

    @FXML
    private Label InfoLabel, CardCounter, EQLabel;

    @FXML
    GridPane BattleGrid;

    @FXML
    Pane mygrid0, mygrid1, mygrid3, mygrid5, mygrid6;

    @FXML
    private Button ExitButton;

    @FXML
    ImageView EQ1,EQ2,EQ3,EQ4;

    @FXML
    Button TakeCardDeck, RageCardDeck, MoneyStack, LostCardDeck;

    String path = "src/main/resources/music/the_witcher.mp3";
    Media media = new Media(new File(path).toURI().toString());
    MediaPlayer mediaPlayer = new MediaPlayer(media);

    String path_sound_click = "src/main/resources/sound/button_release_sound.mp3";
    Media media_click = new Media(new File(path_sound_click).toURI().toString());
    MediaPlayer mediaPlayer_click = new MediaPlayer(media_click);

    String path_sound_page = "src/main/resources/sound/page_button_click.mp3";
    Media media_page = new Media(new File(path_sound_page).toURI().toString());
    MediaPlayer mediaPlayer_page = new MediaPlayer(media_page);

    String path_sound_hover = "src/main/resources/sound/hover_entered_sound.mp3";
    Media media_hover = new Media(new File(path_sound_hover).toURI().toString());
    MediaPlayer mediaPlayer_hover = new MediaPlayer(media_hover);

    public static Server server;
    public static Client client;


    //awaryjne pola do przechwycenia wyniku w razie rozłączenia się, któregoś z graczy
    public static float PLAYER_ONE_POINTS = 0;
    public static float PLAYER_TWO_POINTS = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(SWITCHER == 1){
            server.startGame();
            //server.sendMessageToClient("wysylam mesedz");
            //server.receiveMessageFromClient();
            server.turns();
            //System.out.println("Hehe");
        }
        else {
            client.turns(EQLabel);
            //client.receiveMessageFromServer();
            //client.sendMessageToServer("Wysylam do serwa :)");
        }
    }


    public static void showMessage(String message, Label EQLabel){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                System.out.println("teraz powinienem podmienic");
                EQLabel.setText(message);
                EQLabel.setStyle("-fx-text-fill: green;");
            }
        });
    }

    public void page_sound()
    {
        mediaPlayer_page.setVolume(0.2);
        mediaPlayer_page.stop();
        mediaPlayer_page.seek(Duration.seconds(0));
        mediaPlayer_page.play();
    }
    public void hover_sound()
    {
        mediaPlayer_hover.setVolume(0.5);
        mediaPlayer_hover.stop();
        mediaPlayer_hover.seek(Duration.seconds(0));
        mediaPlayer_hover.play();
    }
    public void click_sound()
    {
        mediaPlayer_click.setVolume(0.5);
        mediaPlayer_click.stop();
        mediaPlayer_click.seek(Duration.seconds(0));
        mediaPlayer_click.play();
    }

    public void stopMusic()
    {
        //WaitingEnemyController waitingEnemyController = new WaitingEnemyController();
        //waitingEnemyController.
        String path = "src/main/resources/music/the_witcher.mp3";
        Media media = new Media(new File(path).toURI().toString());
        MediaPlayer mediaPlayer_music = new MediaPlayer(media);
        mediaPlayer_music.stop();
    }
    @FXML
    protected void onExitButtonClicked(ActionEvent event) throws IOException
    {
        click_sound();
        stopMusic();
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
        click_sound();
    }
    @FXML
    protected void onTakeCardEntered()
    {
        hover_sound();
        InfoLabel.setText("Stos kart postaci - ...Kacper dopisz bo ja nie wiem jak to wyjasnic xd");

    }
    @FXML
    protected void onRageCardClicked()
    {
        click_sound();
    }
    @FXML
    protected void onRageCardEntered()
    {
        hover_sound();
        InfoLabel.setText("Stos kart RAGE - ...Kacper dopisz bo ja nie wiem jak to wyjasnic xd");
    }
    @FXML
    protected void onMoneyStackClicked()
    {
        click_sound();
    }
    @FXML
    protected void onMoneyStackEntered()
    {
        hover_sound();
        InfoLabel.setText("Stos monet - ...Kacper dopisz bo ja nie wiem jak to wyjasnic xd");
    }
    @FXML
    protected void onLostCardClicked()
    {
        click_sound();
    }
    @FXML
    protected void onLostCardEntered()
    {
        hover_sound();
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
        click_sound();
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
        hover_sound();
        InfoLabel.setText("Pole 1");
    }
    @FXML
    protected void onSelectField2Entered()
    {
        hover_sound();
        InfoLabel.setText("Pole 2");
    }
    @FXML
    protected void onSelectField3Entered()
    {
        hover_sound();
        InfoLabel.setText("Pole 3");
    }
    @FXML
    protected void onSelectField4Entered()
    {
        hover_sound();
        InfoLabel.setText("Pole 4");
    }
    @FXML
    protected void onSelectField1Pressed()
    {
        EQ1.setFitHeight(160);
        EQ1.setFitWidth(160);
        EQ1.setX(10);
        EQ1.setY(15);
    }
    @FXML
    protected void onSelectField2Pressed()
    {
        EQ2.setFitHeight(160);
        EQ2.setFitWidth(160);
        EQ2.setX(20);
        EQ2.setY(25);
    }
    @FXML
    protected void onSelectField3Pressed()
    {
        EQ3.setFitHeight(160);
        EQ3.setFitWidth(160);
        EQ3.setX(20);
        EQ3.setY(25);
    }
    @FXML
    protected void onSelectField4Pressed()
    {
        EQ4.setFitHeight(160);
        EQ4.setFitWidth(160);
        EQ4.setX(20);
        EQ4.setY(25);
    }

    @FXML
    protected void onSelectField1Released()
    {
        //Image postac = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/Creatures/Brewek_arts/023.png")));
        //EQ1.setImage(postac);
        EQ1.setFitHeight(180);
        EQ1.setFitWidth(180);
        EQ1.setX(0);
        EQ1.setY(0);

    }
    @FXML
    protected void onSelectField2Released()
    {
        EQ2.setFitHeight(180);
        EQ2.setFitWidth(180);
        EQ2.setX(10);
        EQ2.setY(15);
    }
    @FXML
    protected void onSelectField3Released()
    {
        //EQ3.setImage(null);
        EQ3.setFitHeight(180);
        EQ3.setFitWidth(180);
        EQ3.setX(10);
        EQ3.setY(15);
    }
    @FXML
    protected void onSelectField4Released()
    {
        EQ4.setFitHeight(180);
        EQ4.setFitWidth(180);
        EQ4.setX(10);
        EQ4.setY(15);
    }
    @FXML
    protected void onLeftPageButtonClicked()
    {
        page_sound();
        /*Trzeba tu ogarnac programik na przesuwanie tych kafelek z ekwipunku bo jest problem i stylem
        tak latwo chyba to sie nie naprawi w sensie SelectField1.setstyle("-fx-background-image: sciezkapliku") srednio zadziala jak patrzac w przod
         */
    }
    @FXML
    protected void onRightPageButtonClicked()
    {
        page_sound();
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

        //URL resource = getClass().getResource("src/main/resources/music/the_witcher.mp3");

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



}
