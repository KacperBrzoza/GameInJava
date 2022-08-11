package com.example.Main;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
public class LoginController {
    @FXML
    private Button RegisterButton;
    @FXML
    private Button LoginButton;

    @FXML
    protected void onRegisterButtonClick() {
        RegisterButton.setText("Zarejestrowano");
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