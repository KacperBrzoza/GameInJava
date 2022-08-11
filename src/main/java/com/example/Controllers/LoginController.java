package com.example.Controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class LoginController
{
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Button RegisterButton, ExitButton, LoginButton, OptionButton;

    @FXML
    private TextField LoginTextField, PasswordTextField;

    @FXML
    public void onRegisterButtonClick(ActionEvent event) throws IOException
    {
        //Musialem w taki sposob bo inaczej blad - NullPointerException (Location is required)
        URL url = new File("src/main/resources/com/example/Main/register.fxml").toURI().toURL();
        root = FXMLLoader.load(url);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    protected void onLoginButtonClick()
    {

    }
    @FXML
    protected void onOptionButtonClicked()
    {

    }
    @FXML
    protected void onExitButtonClicked()
    {
        //System.exit(0);
        //Zamiast system.exit(0) uzylem zamykanie stage w ten sposob
        //Mozna jeszcze tak, idk, ktore lepsze
            /*
                ExitButton.setOnAction(e -> Platform.exit());
                Tutaj double click potrzebny wtedy, wiec mozliwe,ze ten sposob najlepszy jest
             */
        Stage stage = (Stage) ExitButton.getScene().getWindow();
        stage.close();
    }
}