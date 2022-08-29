package com.example.Main.Game;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class GameController {

    @FXML
    Label MoneyPlayerValue;

    @FXML
    Label InfoLabel;

    @FXML
    Button ExitButton;










    @FXML
    protected void onExitButtonClicked(ActionEvent event) throws IOException
    {
        URL url = new File("src/main/resources/com/example/Main/Menu/Menu-view.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
