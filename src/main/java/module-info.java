module com.example.Main {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires java.sql;
    requires org.postgresql.jdbc;


    opens com.example.Main to javafx.fxml;
    exports com.example.Main;
    exports com.example.Main.Login;
    opens com.example.Main.Login to javafx.fxml;
    exports com.example.Main.Register;
    opens com.example.Main.Register to javafx.fxml;
    exports com.example.Main.Menu;
    opens com.example.Main.Menu to javafx.fxml;
    exports com.example.Main.Rank;
    opens com.example.Main.Rank to javafx.fxml;
}