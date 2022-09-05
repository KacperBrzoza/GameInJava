package com.example.Main.Register;

import com.example.Main.Service.UserService;
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
import javafx.scene.paint.Color;
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

    RegisterData registerData;
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
            //Poprawione wiadomosci alarmowe (alarmy zakomentowalem, mozna usunac jesli nie potrzebne) - Daniel
            PassMsg.setText("Proszę wypelnic wszystkie pola");
            PassMsg.setStyle("-fx-text-fill: #9e7c26;-fx-font-size: 25pt;");//zolte
        }
        else
        {
            if(!(PasswordTextField.getText().equals(rPasswordTextField.getText())))
            {
                PassMsg.setText("Podane hasła są różne");
                PassMsg.setStyle("-fx-text-fill: #d0312d;-fx-font-size: 25pt;");//czerwone
            }
            else
            {

                if(UserPassVal.isValidUsrnm(LoginTextField.getText()))
                {
                    if(UserPassVal.isValidPass(PasswordTextField.getText()))
                    {
                        registerData = new RegisterData();

                        registerData.setPassword(PasswordTextField.getText());
                        registerData.setUsername(LoginTextField.getText());

                        UserService userService = new UserService();
                        if(userService.isUsernameInUse(registerData.getUsername()))
                        {
                            PassMsg.setText("Uzytkownik o podanej nazwie jest juz w bazie!");
                            PassMsg.setStyle("-fx-text-fill: #d0312d");//czerwone
                        }
                        else
                        {
                            userService.add_user(registerData.getUsername(), registerData.getPassword());
                            //Poprawione wiadomosci alarmowe - Daniel
                            PassMsg.setText("Zarejestrowano pomyslnie!");
                            PassMsg.setStyle("-fx-text-fill: #269e32");//zielone
                        }
                    }
                    else
                    {
                        PassMsg.setText("Haslo niepoprawne! Znakow 8 - 20, znak specjalny, cyfra, duza litera");
                        PassMsg.setStyle("-fx-text-fill: #d0312d;-fx-font-size: 12pt;");//czerwone
                    }
                }
                else
                {
                    PassMsg.setText("Niepoprawna nazwa uzytkownika, 8-30 znakow");
                    PassMsg.setStyle("-fx-text-fill: #d0312d;-fx-font-size: 16pt;");//czerwone
                }
            }
        }
    }

}
