package com.example.Main.Rank;

import com.example.Main.Menu.MenuController;
import com.example.Main.Model.Scores;
import com.example.Main.Service.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RankController implements Initializable
{

    String path_sound_click = "src/main/resources/sound/button_release_sound.mp3";
    Media media_click = new Media(new File(path_sound_click).toURI().toString());
    MediaPlayer mediaPlayer_click = new MediaPlayer(media_click);

    String path_sound_move = "src/main/resources/sound/button_click_sound.mp3";
    Media media_move = new Media(new File(path_sound_move).toURI().toString());
    MediaPlayer mediaPlayer_move = new MediaPlayer(media_move);

    @FXML
    private Button ExitButton;
    @FXML
    private Button BackButton;

    @FXML
    private TableView<Scores> tableView;

    @FXML
    private TableColumn<Scores, String> nickColumn;

    @FXML
    private TableColumn<Scores, Integer> scoreColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        tableView.setPlaceholder(
                new Label("Brak Graczy w Rankingu"));
        UserService userService = new UserService();
        nickColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
        tableView.setItems(userService.getAll());
    }
    //stage.setOnCloseRequest(new EventHandler<WindowEvent>() {     @Override public void handle(WindowEvent t) {         System.out.println("CLOSING");     } });

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
        mediaPlayer_move.seek(Duration.seconds(0));
        mediaPlayer_click.play();
    }
    @FXML
    public void focusMouse()
    {
        BackButton.requestFocus();
    }
    @FXML
    public void onExitButton(ActionEvent event) throws IOException
    {
        Stage stage = (Stage) ExitButton.getScene().getWindow();
        stage.close();
    }
    @FXML
    public void onOptionButton()
    {

    }
    @FXML
    public void onBackButton(ActionEvent event) throws IOException
    {
        MenuController.MenuMusicAllow =true;
        URL url = new File("src/main/resources/com/example/Main/Menu/Menu-view.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }
}
