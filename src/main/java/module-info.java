module com.example.mygymmanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;


    opens com.example.mygymmanager to javafx.fxml;
    exports com.example.mygymmanager;
    exports com.example.mygymmanager.models;
    opens com.example.mygymmanager.models to javafx.fxml;
}