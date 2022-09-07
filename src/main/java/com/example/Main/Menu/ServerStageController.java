package com.example.Main.Menu;

import com.example.NetTools.Server;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;
import java.util.ResourceBundle;

public class ServerStageController implements Initializable {

    private Server server;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            server = new Server(new ServerSocket(3571));
        } catch (IOException e){
            e.printStackTrace();
            System.out.println("Error creating server.");
        }
    }
}
