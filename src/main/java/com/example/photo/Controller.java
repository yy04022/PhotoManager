package com.example.photo;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {
    public void loadPage(String fxmlPath, String title, ActionEvent actionEvent){
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Parent scene = null;
        try {
            scene = FXMLLoader.load(getClass().getResource(fxmlPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setTitle(title);
        stage.setScene(new Scene(scene));
    }
}
