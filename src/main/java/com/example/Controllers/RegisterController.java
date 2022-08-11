package com.example.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class RegisterController
{

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField, rpasswordField;

    @FXML
    public void cancel(ActionEvent event) throws IOException
    {
        URL url = new File("src/main/resources/com/example/Main/hello-login-view.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void register(ActionEvent event) throws IOException
    {
        if(loginField.getText().trim().isEmpty() || passwordField.getText().trim().isEmpty() || rpasswordField.getText().trim().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Proszę wypelnic wszystkie pola");
            alert.show();
        }
        else
        {
            //Zrobic lepsza walidacje - zrobie dzisiaj wieczorem albo jutro - latwe
            //Porownwyanie hasel czy takie same
            RegisterData registerData = new RegisterData();
            registerData.setLogin(loginField.getText());
            registerData.setPassword(passwordField.getText());
            System.out.println(registerData.toString());
            registerData = new RegisterData(registerData.getLogin(), registerData.getPassword());

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Zarejestrowano pomyslnie!");
            alert.show();

            //Tutaj sie kod duplikuje, idk jak wywolac tutaj cancel, moze ktos z was na to wpadnie
            URL url = new File("src/main/resources/com/example/Main/hello-login-view.fxml").toURI().toURL();
            Parent root = FXMLLoader.load(url);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

}
