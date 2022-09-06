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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

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

    String path_sound_bad_input = "src/main/resources/sound/bad_input_sound.mp3";
    Media media_input_bad = new Media(new File(path_sound_bad_input).toURI().toString());
    MediaPlayer mediaPlayer_input_bad = new MediaPlayer(media_input_bad);


    String path_sound_click = "src/main/resources/sound/button_release_sound.mp3";
    Media media_click = new Media(new File(path_sound_click).toURI().toString());
    MediaPlayer mediaPlayer_click = new MediaPlayer(media_click);

    String path_sound_move = "src/main/resources/sound/button_click_sound.mp3";
    Media media_move = new Media(new File(path_sound_move).toURI().toString());
    MediaPlayer mediaPlayer_move = new MediaPlayer(media_move);
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
    public void onMouseClicked()
    {
        mediaPlayer_click.setVolume(0.5);
        mediaPlayer_click.stop();
        mediaPlayer_click.seek(Duration.seconds(0));
        mediaPlayer_click.play();
    }

    @FXML
    public void onMouseEntered()
    {
        mediaPlayer_move.setVolume(0.5);
        mediaPlayer_move.stop();
        mediaPlayer_move.seek(Duration.seconds(0));
        mediaPlayer_move.play();
    }

    @FXML
    public void register(ActionEvent event) throws IOException
    {
        mediaPlayer_input_bad.setVolume(0.5);
        if(LoginTextField.getText().trim().isEmpty() || PasswordTextField.getText().trim().isEmpty() || rPasswordTextField.getText().trim().isEmpty())
        {
            mediaPlayer_input_bad.stop();
            mediaPlayer_input_bad.seek(Duration.seconds(0));
            mediaPlayer_input_bad.play();
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
