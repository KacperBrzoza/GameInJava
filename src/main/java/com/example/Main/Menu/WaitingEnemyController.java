package com.example.Main.Menu;

import com.example.Main.Game.GameController;
import com.example.NetTools.Client;
import com.example.NetTools.Server;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class WaitingEnemyController implements Initializable {

    @FXML
    private Button GameButton;
    @FXML
    private Button ExitButton;
    @FXML
    Pane AllScreen;
    @FXML
    Label WaitLabel;
    public static int SWITCHER =2;
    private static final int PORT_NUMBER = 3571;

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
    private void FadeIn()
    {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2),AllScreen);
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
        if(SWITCHER == 1){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        GameController.server = new Server(new ServerSocket(PORT_NUMBER));
                        FadeIn();
                        //changeStage();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        }else{
            try {
                GameController.client = new Client(new Socket("localhost", PORT_NUMBER));
                FadeIn();
                //changeStage();
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
                /*URL url = null;
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
                }*/
                FXMLLoader loader_temp = new FXMLLoader(getClass().getResource("/com/example/Main/Menu/Menu-view.fxml"));
                try {
                    Parent root = loader_temp.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                MenuController controller_menu = loader_temp.getController();
                System.out.println("test wyciszania ktory nie moze wyciszyc ://");
                controller_menu.Music_menu_on_off(false);

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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/Main/Menu/Menu-view.fxml"));
        Parent root = loader.load();
        MenuController controller_menu = loader.getController();
        System.out.println("test");
        //controller_menu.Music_menu_on_off(false);
        //URL url = new File("src/main/resources/com/example/Main/Menu/Menu-view.fxml").toURI().toURL();
        //Testowe przejscie do ekranu gry aby sprawdzic dzialanie gui
        //URL url = new File("src/main/resources/com/example/Main/Menu/Menu-view.fxml").toURI().toURL();
        //Parent root = FXMLLoader.load(url);
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
