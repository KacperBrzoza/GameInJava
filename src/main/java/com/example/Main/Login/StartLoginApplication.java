package com.example.Main.Login;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class StartLoginApplication extends Application
{

    //public static DatabaseConnection databaseConnection; //Do polaczenia z bd
    //SessionFactory zawarte jest w EntityManager

    @Override
    public void start(Stage stage) throws IOException
    {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/Main/Login/hello-login-view.fxml"));
        Parent root = loader.load();
        //URL url = new File("src/main/resources/com/example/Main/Login/hello-login-view.fxml").toURI().toURL();
        //Parent root = FXMLLoader.load(url);
        LoginController controller = loader.getController();
        controller.ambient_music(true);
        Scene scene = new Scene(root);
        stage.setHeight(1920);
        stage.setWidth(1920);
        stage.initStyle(StageStyle.UNDECORATED);
        Image icon = new Image(String.valueOf(StartLoginApplication.class.getResource("/icon/icon_KTC_og-size.png")));
        stage.getIcons().add(icon);
        stage.setTitle("Kingdoms Towers Collide");
        //controller.ambient_music(false);
        stage.setX(0);
        stage.setY(0);
        scene.getStylesheets().add(getClass().getResource("/style/style-class.css").toExternalForm());
        stage.setMaximized(true);
        stage.setResizable(false);
        stage.setScene(scene);
        //ambient_music();
        stage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}