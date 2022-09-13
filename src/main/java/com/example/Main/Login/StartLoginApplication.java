package com.example.Main.Login;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;
import java.io.IOException;


public class StartLoginApplication extends Application
{
    public static Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    public static float getScreenWidth() {return (float) screenSize.getWidth();}
    public static float getScreenHeight() {return (float) screenSize.getHeight();}
    public static boolean checkScreenSize()
    {
        if(screenSize.getHeight()==1080&&screenSize.getWidth()==1920)
        {
            return true;
        }
        else
            return false;
    }

    @Override
    public void start(Stage stage) throws IOException
    {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/Main/Login/hello-login-view.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setHeight(1080);
        stage.setWidth(1920);

        //set Stage boundaries to visible bounds of the main screen

        //System.out.println("w:" + screenSize.getWidth() + "h:" + screenSize.getHeight() );
        Image icon = new Image(String.valueOf(StartLoginApplication.class.getResource("/icon/icon_KTC_og-size.png")));
        stage.getIcons().add(icon);
        stage.setTitle("Kingdoms Towers Collide");
        if(checkScreenSize())
        {
            stage.setWidth(screenSize.getWidth());
            stage.setHeight(screenSize.getHeight());
            stage.setMaximized(true);
            stage.initStyle(StageStyle.UNDECORATED);

        }
        else
        {
            stage.setWidth(screenSize.getWidth());
            stage.setHeight(screenSize.getHeight()-40);
            stage.setMaximized(false);
            stage.setX(0);
            stage.setY(0);
        }
        stage.setX(0);
        stage.setY(0);
        scene.getStylesheets().add(getClass().getResource("/style/style-class.css").toExternalForm());
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}