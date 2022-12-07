module com.apka {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.apka to javafx.fxml;
    exports com.apka;
}