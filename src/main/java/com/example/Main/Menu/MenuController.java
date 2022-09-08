package com.example.Main.Menu;

import com.example.Main.Login.LoginController;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {

    @FXML
    private AnchorPane AllScreen;
    @FXML
    private Button ExitButton;
    @FXML
    private HBox NotificationPane;
public static boolean MenuMusicAllow;

    String path_sound_click = "src/main/resources/sound/button_release_sound.mp3";
    Media media_click = new Media(new File(path_sound_click).toURI().toString());
    MediaPlayer mediaPlayer_click = new MediaPlayer(media_click);

    String path_sound_move = "src/main/resources/sound/button_click_sound.mp3";
    Media media_move = new Media(new File(path_sound_move).toURI().toString());
    MediaPlayer mediaPlayer_move = new MediaPlayer(media_move);
    String path_music_menu = "src/main/resources/music/PORTMONETKA_MUSIC_MENU.mp3";
    Media music_menu = new Media(new File(path_music_menu).toURI().toString());
    MediaPlayer mediaPlayer_menu_music = new MediaPlayer(music_menu);


    @FXML
    void FadeOut()
    {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(3),AllScreen);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();
        LoginController.FadeTransitionAllow=false;
    }
    @FXML
    public void onMouseEntered()
    {
        mediaPlayer_move.setVolume(0.5);
        mediaPlayer_move.stop();
        mediaPlayer_move.seek(Duration.seconds(0));
        mediaPlayer_move.play();
    }
    public void onMouseClick()
    {
        mediaPlayer_click.setVolume(0.5);
        mediaPlayer_click.stop();
        mediaPlayer_move.seek(Duration.seconds(0));
        mediaPlayer_click.play();
    }
    public void Music_menu_on_off(boolean on_off)
    {
        if(on_off) {
            mediaPlayer_menu_music.play(); /*Z TYM JEST DZIWNY PROBLEM BO NIE DA SIE TEGO WYłACZYC W OGOLE NIGDZIE NIBY CZASEM DZIALA ALE NIE ZAWSZE*/
            //System.out.println("play");
        }
        else {
            //System.out.println("stop");
            mediaPlayer_menu_music.stop();
        }

    }

    @FXML
    public void onExitButton(ActionEvent event) throws IOException
    {
        Stage stage = (Stage) ExitButton.getScene().getWindow();
        stage.close();
    }
    @FXML
    public void onOptionButton()
    {

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        mediaPlayer_menu_music.setVolume(0.25);
        mediaPlayer_menu_music.setCycleCount(MediaPlayer.INDEFINITE);
        if(MenuMusicAllow)
        {
            Music_menu_on_off(true);
            MenuMusicAllow=false;
        }
        if(LoginController.FadeTransitionAllow)
        {
            AllScreen.setOpacity(0);
            FadeOut();
        }

    }

    @FXML
    public void onPlayButton(ActionEvent event) throws IOException
    {
        mediaPlayer_menu_music.stop();
        URL url = new File("src/main/resources/com/example/Main/Menu/Waiting-Oponent-view.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();



        //poniżej znajduje się propozycja połączenia rozgrywki z programem w formie takiego pseudokodu

        /*   !!! ROBOTY PROGRAMISTYCZNE !!!
        ip_adres = null;
        czy_bede_serwerem = 0;
        do {
            //jeżeli w tabeli z ipkami coś jest
            if (ConnectionMenager.search() != null / 0) {
                //iteruj po tych wynikach tzn...
                for(each -> wyniki) {
                    //... sprawdzaj po kolei z którym ip można się połączyć
                    //jeśli z tym można, to ...
                        //ip_adress = ten działający adres
                        //break;
                }
            }
            //jeżeli w tabeli jest pusto
             else {
                //dodaj moje ip do tabeli
                czy_bede_serwerem = 1;
            }
        }
        while(ip_adres == null && czy_bede_serwerem == 0);
*/

        //Komentuje bo odpala sie i potem trzeba wylaczac xd
        /*
        int czy_bede_serwerem = 1;
        if(czy_bede_serwerem == 0){
            ClientThread ct = new ClientThread("188.146.12.132");
            ct.start();
        }
        else{
            ServerThread st = new ServerThread();
            st.start();
        }

         */

         

    }
    @FXML
    public void onPlayWithButton(ActionEvent event) throws IOException
    {
        URL url = new File("src/main/resources/com/example/Main/Menu/Find-Oponent-view.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void onRankButton(ActionEvent event) throws IOException
    {
        Music_menu_on_off(false);
        URL url = new File("src/main/resources/com/example/Main/Rank/rank-view.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void onCreditsButton()
    {

    }
    @FXML
    public void onLogoutButton(ActionEvent event) throws IOException
    {
        LoginController.FadeTransitionAllow=true;
        //System.out.println("test wylogowania");
        //Music_menu_on_off(false);
        mediaPlayer_menu_music.stop();
        MenuMusicAllow=false;
        NotificationPane.setVisible(false);
        URL url = new File("src/main/resources/com/example/Main/Login/hello-login-view.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }
}
