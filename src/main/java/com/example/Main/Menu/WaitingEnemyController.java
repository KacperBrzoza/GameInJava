package com.example.Main.Menu;

import com.example.Main.Game.GameController;
import com.example.NetTools.Client;
import com.example.NetTools.Server;
import com.example.NetTools.Test;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class WaitingEnemyController implements Initializable {

    @FXML
    private Button GameButton;
    @FXML
    private Button ExitButton;

    public static int SWITCHER = 2;
    private static final int PORT_NUMBER = 3571;

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

    public void onMediaStart()
    {
        mediaPlayer_music.setVolume(0.1);
        mediaPlayer_music.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer_music.play();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(SWITCHER == 1){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        GameController.server = new Server(new ServerSocket(PORT_NUMBER));
                        changeStage();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        }else{
            try {
                GameController.client = new Client(new Socket("localhost", PORT_NUMBER));
                changeStage();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void changeStage(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                URL url = null;
                try {
                    url = new File("src/main/resources/com/example/Main/Game/Game.fxml").toURI().toURL();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                //onMediaStart();
                Parent root = null;
                try {
                    root = FXMLLoader.load(url);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Stage stage = (Stage) ExitButton.getScene().getWindow();
                Scene scene = new Scene(root);

                stage.setResizable(false);
                stage.setMaximized(true);
                stage.setScene(scene);
                stage.show();
            }
        });
    }

    @FXML
    public void onBackButton(ActionEvent event) throws IOException
    {
        //URL url = new File("src/main/resources/com/example/Main/Menu/Menu-view.fxml").toURI().toURL();
        //Testowe przejscie do ekranu gry aby sprawdzic dzialanie gui
        URL url = new File("src/main/resources/com/example/Main/Game/Game.fxml").toURI().toURL();
        onMediaStart();
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
