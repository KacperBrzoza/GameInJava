module com.example.cardgame {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.Main to javafx.fxml;
    exports com.example.Main;
}