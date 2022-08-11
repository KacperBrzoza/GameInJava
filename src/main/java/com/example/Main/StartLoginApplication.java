package com.example.Main;

import com.example.Controllers.DatabaseConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class StartLoginApplication extends Application {

    public static DatabaseConnection databaseConnection; //Do polaczenia z bd
    @Override
    public void start(Stage stage) throws IOException {


        FXMLLoader fxmlLoader = new FXMLLoader(StartLoginApplication.class.getResource("hello-login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1900, 950);
        Image icon = new Image(String.valueOf(StartLoginApplication.class.getResource("/icon/icon_KTC_og-size.png")));
        stage.getIcons().add(icon);
        stage.setTitle("Kingdoms Towers Collide");
        stage.setX(0);
        stage.setY(0);
        stage.setResizable(false);
        stage.setFullScreen(true);
        //stage.setMaximized(true);
        stage.setFullScreenExitHint("Aby zminimalizować okno wciśnij ESC");
        ambient_music();
        scene.getStylesheets().add(getClass().getResource("/style/style-class.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public void ambient_music()
    {
        String path = "src/main/resources/music/ambience_sound.mp3";
        Media media = new Media(new File(path).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setVolume(50);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    }

    public static void main(String[] args)
    {
        try
        {
            databaseConnection =new DatabaseConnection();
            //databaseConnection.create_base(); - Ogolnie zakomentowane,tylko jesli pierwszy raz odpalacie to wtedy z tym zeby utworzyc tabele
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        catch (ClassNotFoundException ex)
        {
            ex.printStackTrace();
        }
        launch(args);
    }
}