package com.example.Main.Menu;

import com.example.Main.Game.GameController;
import com.example.Main.Login.Memory;
import com.example.Main.Service.UserService;
import com.example.NetTools.Client;
import com.example.NetTools.Server;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.net.*;
import java.util.ResourceBundle;

import static com.example.Main.Game.GameController.client;
import static com.example.Main.Game.GameController.server;


public class WaitingEnemyController implements Initializable {

    @FXML
    private Button GameButton;
    @FXML
    private Button ExitButton;
    @FXML
    Pane AllScreen;
    @FXML
    Label WaitLabel;
    @FXML
    private Button BackButton;
    public static int SWITCHER;
    private static final int PORT_NUMBER = 3571;

    private UserService userService;

    String path_sound_click = "src/main/resources/sound/button_release_sound.mp3";
    Media media_click = new Media(new File(path_sound_click).toURI().toString());
    MediaPlayer mediaPlayer_click = new MediaPlayer(media_click);

    String path_sound_move = "src/main/resources/sound/button_click_sound.mp3";
    Media media_move = new Media(new File(path_sound_move).toURI().toString());
    MediaPlayer mediaPlayer_move = new MediaPlayer(media_move);

    String path_music_menu = "src/main/resources/music/PORTMONETKA_MUSIC_MENU.mp3";
    Media music_menu = new Media(new File(path_music_menu).toURI().toString());
    MediaPlayer mediaPlayer_menu_music = new MediaPlayer(music_menu);

    public static boolean BackButtonClickAllow = true;

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
    public void focusMouse()
    {
        BackButton.requestFocus();
    }
    @FXML
    private void FadeIn()
    {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1),AllScreen);
        WaitLabel.setStyle("-fx-text-fill: #269e32;");
        WaitLabel.setText("Znaleziono Gracza!");
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0);
        fadeTransition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                changeStage();
            }
        });
        fadeTransition.play();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mediaPlayer_menu_music.setVolume(0.25);
        mediaPlayer_menu_music.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer_menu_music.play();
        BackButtonClickAllow=true;
        userService = new UserService();
        try
        {
            InetAddress IP = InetAddress.getLocalHost();
            userService.check_in_base_IP(IP.getHostAddress());
        } catch (UnknownHostException e)
        {
            throw new RuntimeException(e);
        }
        //Jestesmy serwerem
        if(SWITCHER == 1){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        server = new Server(new ServerSocket(PORT_NUMBER));
                        server.sendAndListen();
                        if(BackButtonClickAllow) {

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {

                                    FadeIn();
                                }
                            });
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        //Jestesmy klientem
        }else{
            try {
                GameController.client = new Client(new Socket(String.valueOf(userService.get_Ip()), PORT_NUMBER));
                userService.delete_IP();
                client.listenAndSend(Memory.memory.getUsername());
                FadeIn();
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
                mediaPlayer_menu_music.stop();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/Main/Game/Game.fxml"));
                Parent root = null;
                try {
                    root = loader.load();
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
        mediaPlayer_menu_music.stop();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    BackButtonClickAllow=false;
                    client = new Client(new Socket(InetAddress.getLocalHost().getHostAddress(), PORT_NUMBER));
                    userService = new UserService();
                    client.sendMessageToServer(Memory.memory.getUsername());
                    userService.delete_IP();
                    server.closeEverything();
                    client.closeEverything();

                } catch (IOException e) {
                    System.out.println("błąd zamykania");
                    throw new RuntimeException(e);
                }
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        MenuController.MenuMusicAllow =true;
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/Main/Menu/Menu-view.fxml"));
                        Parent root = null;
                        try {
                            root = loader.load();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        Stage stage = (Stage) BackButton.getScene().getWindow();
                        Scene scene = new Scene(root);
                        stage.setResizable(false);
                        stage.setMaximized(true);
                        stage.setScene(scene);
                        stage.show();
                    }
                });
            }
        }).start();

    }
    @FXML
    public void onExitButton(ActionEvent event) throws IOException
    {
        userService = new UserService();
        userService.delete_IP();

        userService.set_Usage_false(Memory.memory.getUsername());

        Stage stage = (Stage) ExitButton.getScene().getWindow();
        stage.close();

        System.exit(1);
    }
}
