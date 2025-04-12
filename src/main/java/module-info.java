module com.example.demo1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens com.example.demo1 to javafx.fxml;
    exports com.example.demo1;
    opens com.example.demo1.controller to javafx.fxml;
    opens com.example.demo1.model to javafx.fxml;
}