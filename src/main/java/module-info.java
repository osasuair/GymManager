module com.example.gymmanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;


    opens com.example.gymmanagementsystem to javafx.fxml;
    exports com.example.gymmanagementsystem;
    exports com.example.gymmanagementsystem.models;
    opens com.example.gymmanagementsystem.models to javafx.fxml;
}