module controller {
    requires javafx.controls;
    requires javafx.fxml;


    opens controller to javafx.fxml;
    opens model to javafx.fxml;
//    opens com.example.photo to javafx.fxml;
//    exports com.example.photo;
    exports controller;
    exports model;
}