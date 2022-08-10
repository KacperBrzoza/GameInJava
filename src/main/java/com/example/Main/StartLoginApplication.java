package com.example.Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;



import java.io.IOException;

public class StartLoginApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {


        FXMLLoader fxmlLoader = new FXMLLoader(StartLoginApplication.class.getResource("hello-login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1600, 900);
        Image icon = new Image(String.valueOf(StartLoginApplication.class.getResource("/icon/icon_KTC_og-size.png")));
        stage.getIcons().add(icon);
        stage.setTitle("Kingdoms Towers Collide");
        stage.setX(0);
        stage.setY(0);
        stage.setResizable(false);
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("Aby zminimalizować okno wciśnij ESC");
        scene.getStylesheets().add(getClass().getResource("style-class.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {launch(args);}
}