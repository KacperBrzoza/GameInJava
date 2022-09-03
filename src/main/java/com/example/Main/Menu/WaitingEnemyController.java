package com.example.Main.Menu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class WaitingEnemyController {
    @FXML
    private Button ExitButton;

    String path_sound_click = "src/main/resources/sound/button_release_sound.mp3";
    Media media_click = new Media(new File(path_sound_click).toURI().toString());
    MediaPlayer mediaPlayer_click = new MediaPlayer(media_click);

    String path_sound_move = "src/main/resources/sound/button_click_sound.mp3";
    Media media_move = new Media(new File(path_sound_move).toURI().toString());
    MediaPlayer mediaPlayer_move = new MediaPlayer(media_move);

    String path = "src/main/resources/music/the_witcher.mp3";
    Media media = new Media(new File(path).toURI().toString());
    MediaPlayer mediaPlayer_music = new MediaPlayer(media);
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
    public void onBackButton(ActionEvent event) throws IOException
    {
        //URL url = new File("src/main/resources/com/example/Main/Menu/Menu-view.fxml").toURI().toURL();
        //Testowe przejscie do ekranu gry aby sprawdzic dzialanie gui
        mediaPlayer_music.setVolume(0.1);
        mediaPlayer_music.setCycleCount(MediaPlayer.INDEFINITE);
        //mediaPlayer_music.play();
        URL url = new File("src/main/resources/com/example/Main/Game/Game.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setResizable(false);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();

    }
    @FXML
    public void onExitButton(ActionEvent event) throws IOException
    {

        Stage stage = (Stage) ExitButton.getScene().getWindow();
        stage.close();
    }
}
