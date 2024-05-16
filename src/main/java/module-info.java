module com.example.sensitivityconverter {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.sensitivityconverter to javafx.fxml;
    exports com.example.sensitivityconverter;
}