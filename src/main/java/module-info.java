module com.example.photo {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.photo to javafx.fxml;
    exports com.example.photo;
}