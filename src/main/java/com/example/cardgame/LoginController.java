package com.example.cardgame;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class LoginController {
    @FXML
    private Button RegisterButton;
    @FXML
    private Button LoginButton;

    @FXML
    protected void onRegisterButtonClick() {

    }
    @FXML
    protected void onLoginButtonClick() {
        LoginButton.setText("Zalogowano");
    }
    @FXML
    protected void onOptionButtonClicked()
    {

    }
    @FXML
    protected void onExitButtonClicked()
    {
        System.exit(0);
    }
}