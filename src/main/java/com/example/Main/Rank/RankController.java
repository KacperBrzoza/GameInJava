package com.example.Main.Rank;

import com.example.Main.Model.UserData;
import com.example.Main.PersistenceManager.PersistenceManager;
import com.example.Main.Register.RegisterData;
import com.example.Main.Service.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.persistence.Column;
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
    private TableView<UserData> tableView;

    @FXML
    private TableColumn<UserData, String> nickColumn;
    @FXML
    private TableColumn<UserData, Integer> uidColumn;
    @FXML
    private TableColumn<UserData, String> passwordColumn;

    /*
    @FXML
    private TableColumn<String ,String> LostColumn;

    @FXML
    private TableColumn<String ,String> WinColumn;

    @FXML
    private TableColumn<String ,String> RankColumn;

    @FXML
    private TableColumn<String ,String> PointsColumn;
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        tableView = new TableView<>();
        UserService userService = new UserService();
        //userService.show_user();
        nickColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        uidColumn.setCellValueFactory(new PropertyValueFactory<>("uid"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));

        //tableView.setItems(userService.show_user());
        //tableView.getItems().addAll(userService.show_user());
        userService.show_user();
        tableView.setItems(userService.observableList);
        userService.findAll().forEach(System.out::println);
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
        mediaPlayer_move.seek(Duration.seconds(0));
        mediaPlayer_click.play();
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
