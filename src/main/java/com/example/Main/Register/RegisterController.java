package com.example.Main.Register;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class RegisterController
{

    @FXML
    private TextField LoginTextField;
    @FXML
    private Button ExitButton;
    @FXML
    private Label PassMsg;
    @FXML
    private PasswordField PasswordTextField, rPasswordTextField;


    @FXML
    public void onExitButtonClicked(ActionEvent event) throws IOException
    {
        Stage stage = (Stage) ExitButton.getScene().getWindow();
        stage.close();
    }
    @FXML
    public void cancel(ActionEvent event) throws IOException
    {
        URL url = new File("src/main/resources/com/example/Main/Login/hello-login-view.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void register(ActionEvent event) throws IOException
    {
        if(LoginTextField.getText().trim().isEmpty() || PasswordTextField.getText().trim().isEmpty() || rPasswordTextField.getText().trim().isEmpty())
        {
            //Alert alert = new Alert(Alert.AlertType.ERROR);
            //alert.setContentText("Proszę wypelnic wszystkie pola");
            //alert.show();
            //Poprawione wiadomosci alarmowe (alarmy zakomentowalem, mozna usunac jesli nie potrzebne) - Daniel
            PassMsg.setText("Proszę wypelnic wszystkie pola");
            PassMsg.setStyle("-fx-text-fill: #9e7c26;-fx-font-size: 25pt;");//zolte
            //
        }
        else
        {
            //Porownwyanie hasel czy takie same
            RegisterData registerData = new RegisterData();
            registerData.setLogin(LoginTextField.getText());
            registerData.setPassword(PasswordTextField.getText());


                //DatabaseConnection databaseConnection = new DatabaseConnection();
                //databaseConnection.add_user(registerData.getLogin(),registerData.getPassword());

            //UserService userService = new UserService();
            //userService.addUser(LoginTextField.getText(), PasswordTextField.getText());
            //Poprawione wiadomosci alarmowe - Daniel
            PassMsg.setText("Zarejestrowano pomyslnie!");
            PassMsg.setStyle("-fx-text-fill: #269e32");//zielone
            //
            //Tutaj sie kod duplikuje, idk jak wywolac tutaj cancel, moze ktos z was na to wpadnie

            //Ja bym to usunął i jedynie dawal ze "Zarejestrowano pomyslnie!" a wracane by bylo juz przyciskiem "Wróć" - Daniel
            /*URL url = new File("src/main/resources/com/example/Main/Login/hello-login-view.fxml").toURI().toURL();
            Parent root = FXMLLoader.load(url);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.setMaximized(true);
            stage.setScene(scene);
            stage.show();
            stage.show();*/
        }
    }

}
