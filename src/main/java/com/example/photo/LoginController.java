package com.example.photo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.EventObject;
import java.util.Scanner;

public class LoginController {

    public TextArea userNameTF;

    public void onLoginButtonClick(ActionEvent actionEvent) throws FileNotFoundException {
        String username = userNameTF.getText();
        File file = new File("src/main/java/com/example/photo/users.txt");
        Scanner scanner = new Scanner(file);
        while(scanner.hasNext()){
            String input = scanner.next();
            if(username.equals(input) && input.equals("admin")){
                loadPage("Adminpage.fxml","Adminpage",actionEvent);
            }else if(username.equals(input)){
                loadPage("UserDashboard.fxml","UserDashboard",actionEvent);
            }
        }


    }
    public void loadPage(String fxmlPath,String title,ActionEvent actionEvent){
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