package com.example.Main.Menu;

import com.example.Main.Login.StartLoginApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class FindEnemyController {


    @FXML
    private Button ExitButton;
    @FXML
    private Label SendInviteLabel;

    String path_sound_click = "src/main/resources/sound/button_release_sound.mp3";
    Media media_click = new Media(new File(path_sound_click).toURI().toString());
    MediaPlayer mediaPlayer_click = new MediaPlayer(media_click);

    String path_sound_move = "src/main/resources/sound/button_click_sound.mp3";
    Media media_move = new Media(new File(path_sound_move).toURI().toString());
    MediaPlayer mediaPlayer_move = new MediaPlayer(media_move);
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
    @FXML
    public void onSendButton(ActionEvent event) throws IOException
    {

        if(false)//Obsluga wysylania czy uzytkownik istnieje w bazie
        {
            SendInviteLabel.setText("Taki użytkownik nie istnieje.");
        }
        else if(true)
        {
            SendInviteLabel.setText("Wysłano zaproszenie!");
            SendInviteLabel.setStyle("-fx-text-fill: #269e32");
        }


    }

    @FXML
    public void onBackButton(ActionEvent event) throws IOException
    {
        URL url = new File("src/main/resources/com/example/Main/Menu/Menu-view.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setResizable(false);
        if(StartLoginApplication.checkScreenSize())
        {
            stage.setMaximized(true);
        }
        else
        {
            stage.setX(0);
            stage.setY(0);
            stage.setMaximized(false);
        }
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void onOptionButton()
    {

    }
    @FXML
    public void onExitButton(ActionEvent event) throws IOException
    {
        Stage stage = (Stage) ExitButton.getScene().getWindow();
        stage.close();
    }
}
