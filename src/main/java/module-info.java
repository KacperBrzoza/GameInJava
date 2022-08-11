module com.example.Main {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires java.sql;
    requires org.postgresql.jdbc;


    opens com.example.Main to javafx.fxml;
    exports com.example.Main;
    exports com.example.Controllers;
    opens com.example.Controllers to javafx.fxml;
}