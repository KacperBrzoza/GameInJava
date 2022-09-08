package com.example.Main.Login;

import com.example.Main.Menu.MenuController;
import com.example.Main.Register.RegisterData;
import com.example.Main.Service.UserService;
import javafx.animation.FadeTransition;
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
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class LoginController implements Initializable
{
    public static boolean FadeTransitionAllow=false;
    private Stage stage;
    private Scene scene;
    private Parent root;
    boolean Music_status_option=true;

    @FXML
    private Button RegisterButton, ExitButton, LoginButton, OptionButton;
    @FXML
    private Label PassMsg;
    @FXML
    private TextField LoginTextField, PasswordTextField;
    @FXML
    private AnchorPane AllScreen;

    String path_sound_click = "src/main/resources/sound/button_release_sound.mp3";
    Media media_click = new Media(new File(path_sound_click).toURI().toString());
    MediaPlayer mediaPlayer_click = new MediaPlayer(media_click);

    String path_sound_move = "src/main/resources/sound/button_click_sound.mp3";
    Media media_move = new Media(new File(path_sound_move).toURI().toString());
    MediaPlayer mediaPlayer_move = new MediaPlayer(media_move);

    String path_sound_good_login = "src/main/resources/sound/login_good_input_sound.mp3";
    Media media_login_good = new Media(new File(path_sound_good_login).toURI().toString());
    MediaPlayer mediaPlayer_login_good = new MediaPlayer(media_login_good);

    String path_sound_bad_input = "src/main/resources/sound/bad_input_sound.mp3";
    Media media_input_bad = new Media(new File(path_sound_bad_input).toURI().toString());
    MediaPlayer mediaPlayer_input_bad = new MediaPlayer(media_input_bad);

    String path = "src/main/resources/music/ambience_sound.mp3";
    Media media = new Media(new File(path).toURI().toString());
    MediaPlayer mediaPlayer = new MediaPlayer(media);

    public void ambient_music(boolean on_off)
    {
        mediaPlayer.setVolume(0.8);
        if(on_off) {
            mediaPlayer.play();
            //System.out.println("on");
        }
        else {
            mediaPlayer.stop();
            //System.out.println("off");
        }
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    }
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
        mediaPlayer_click.seek(Duration.seconds(0));
        mediaPlayer_click.play();
    }
    @FXML
    public void onRegisterButtonClick(ActionEvent event) throws IOException, InterruptedException {
        //Musialem w taki sposob bo inaczej blad - NullPointerException (Location is required)
        URL url = new File("src/main/resources/com/example/Main/Register/register-view.fxml").toURI().toURL();
        root = FXMLLoader.load(url);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setResizable(false);
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/style/style-class.css").toExternalForm());
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
        //osobna_funkcja();
    }


     @FXML
     private void FadeIn()
     {
         FadeTransitionAllow=true;
         FadeTransition fadeTransition = new FadeTransition(Duration.seconds(3),AllScreen);
         fadeTransition.setFromValue(1.0);
         fadeTransition.setToValue(0);
         fadeTransition.setOnFinished(new EventHandler<ActionEvent>() {
             @Override
             public void handle(ActionEvent event) {
                 try {
                     ChangeScene();
                 } catch (IOException e) {
                     e.printStackTrace();
                 }
             }
         });
         fadeTransition.play();
     }
     @FXML
     private void ChangeScene() throws IOException {
         MenuController.MenuMusicAllow=true;
         URL url_menu = new File("src/main/resources/com/example/Main/Menu/Menu-view.fxml").toURI().toURL();
         root = FXMLLoader.load(url_menu);
         stage = (Stage) PassMsg.getScene().getWindow();
         scene = new Scene(root);
         stage.setResizable(false);
         scene.getStylesheets().add(getClass().getResource("/style/style-class.css").toExternalForm());
         stage.setMaximized(true);
         stage.setScene(scene);
         stage.show();
     }

    @FXML
    protected void onLoginButtonClick() {

        UserService userService = new UserService();
        RegisterData registerData = new RegisterData();
        mediaPlayer_input_bad.setVolume(0.5);
        mediaPlayer_login_good.setVolume(0.5);
        if(LoginTextField.getText().trim().isEmpty() || PasswordTextField.getText().trim().isEmpty())//brak wypelnionych pol
        {
            mediaPlayer_input_bad.stop();
            mediaPlayer_input_bad.seek(Duration.seconds(0));
            mediaPlayer_input_bad.play();
            PassMsg.setText("Wypełnij wszystkie pola!");
            PassMsg.setStyle("-fx-text-fill: #9e7c26");
        }
        else
        {
            registerData.setUsername(LoginTextField.getText());
            registerData.setPassword(PasswordTextField.getText());
            if(!userService.isUsernameInUse(registerData.getUsername())) //gdy nie ma uzytkownika w bazie
            {
                mediaPlayer_input_bad.stop();
                mediaPlayer_input_bad.seek(Duration.seconds(0));
                mediaPlayer_input_bad.play();
                PassMsg.setText("Taki użytkownik nie istnieje.");
                PassMsg.setStyle("-fx-text-fill: #d0312d;-fx-font-size: 18pt;");//czerwone
            }
            else if(userService.isUsernameInUse(registerData.getUsername()) && !(userService.isCorrect(registerData.getUsername(), registerData.getPassword()))) //jest uzytkownik ale jest zle haslo
            {
                mediaPlayer_input_bad.stop();
                mediaPlayer_input_bad.seek(Duration.seconds(0));
                mediaPlayer_input_bad.play();
                PassMsg.setText("Podano złe hasło!");
                PassMsg.setStyle("-fx-text-fill: #d0312d;-fx-font-size: 18pt;");//czerwone
            }
            else //Dobre haslo i dobry uzytkownik
            {
                Memory.memory = registerData;
                //System.out.println(Memory.memory.getUsername());
                PassMsg.setStyle("-fx-text-fill: #269e32");
                PassMsg.setText("Zalogowano pomyślnie.");
                mediaPlayer_login_good.stop();
                mediaPlayer_login_good.seek(Duration.seconds(0));
                mediaPlayer_login_good.play();
                mediaPlayer.stop();
                FadeIn();
                //kontrolna zmiana muzy
                //
                //
                //FadeIn(event); //Przyciemnienie na przejście (nie dziala bo trzeba zrobic thready ktore beda zajmowac sie innymi procesami)

            }
        }
        /*

        else if(true)
        {
            //FadeIn(event); //Przyciemnienie na przejście (nie dziala bo trzeba zrobic thready ktore beda zajmowac sie innymi procesami)
            mediaPlayer_login_good.stop();
            mediaPlayer_login_good.seek(Duration.seconds(0));
            mediaPlayer_login_good.play();
            PassMsg.setText("Zalogowano pomyślnie.");
            //kontrolna zmiana muzy
            //
            //
            PassMsg.setStyle("-fx-text-fill: #269e32");
            URL url_menu = new File("src/main/resources/com/example/Main/Menu/Menu-view.fxml").toURI().toURL();
            root = FXMLLoader.load(url_menu);
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setResizable(false);
            scene.getStylesheets().add(getClass().getResource("/style/style-class.css").toExternalForm());
            stage.setMaximized(true);
            stage.setScene(scene);
            stage.show();
        }
         */


    }
    @FXML
    protected void onOptionButtonClicked()
    {
        //Prowizoryczne wyciszanie muzy ale tylko w głównym kontrolerze "LoginController" bo nie ma jeszcze komunikacji między kontrolerami
        if(Music_status_option==true)
        {
            Music_status_option=false;
            ambient_music(false);
        }
        else
        {
            Music_status_option=true;
            ambient_music(true);
        }
    }
    @FXML
    protected void onExitButtonClicked()
    {
        //Zamiast system.exit(0) uzylem zamykanie stage w ten sposob
        //Mozna jeszcze tak, idk, ktore lepsze
            /*
                ExitButton.setOnAction(e -> Platform.exit());
                Tutaj double click potrzebny wtedy, wiec mozliwe,ze ten sposob najlepszy jest
             */
        Stage stage = (Stage) ExitButton.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ambient_music(true);
        LoginTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent ke) {
            if (ke.getCode().equals(KeyCode.ENTER)) {
                    onLoginButtonClick();
            }
        }
    });
        PasswordTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent ke) {
        if (ke.getCode().equals(KeyCode.ENTER)) {
            onLoginButtonClick();
        }
    }
    });
    }
}