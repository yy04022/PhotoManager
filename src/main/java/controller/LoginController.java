package controller;

import controller.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import model.Album;
import model.User;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

public class LoginController extends Controller implements Initializable {

    public TextArea userNameTF;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            readUserText();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void onLoginButtonClick(ActionEvent actionEvent) throws FileNotFoundException {


        String username = userNameTF.getText();

        System.out.println(userData.size());
        for (User user : userData) {
            if (username.equals(user.getUsername()) && username.equals("admin")) {
                loginUser = username;
                loadPage("Adminpage.fxml", "AdminController", actionEvent);
                return;
            } else if (username.equals(user.getUsername())) {
                loginUser = username;
                loadPage("UserDashboard.fxml", "UserDashboard", actionEvent);
                return;
            }

        }
        showAlertAddDialog(actionEvent,"Invaild User");
//        public void onLoginButtonClick(ActionEvent actionEvent) throws FileNotFoundException {
//            String username = this.userNameTF.getText();
//            File file = new File("src/main/java/controller/users.txt");
//            Scanner scanner = new Scanner(file);
//
//            while(scanner.hasNext()) {
//                String input = scanner.next();
//                if (username.equals(input) && input.equals("admin")) {
//                    loginUser = username;
//                    this.loadPage("Adminpage.fxml", "AdminController", actionEvent);
//                    break;
//                }
//
//                if (username.equals(input)) {
//                    loginUser = username;
//                    this.loadPage("UserDashboard.fxml", "UserDashboard", actionEvent);
//                    break;
//                }
//
//                if (!username.equals(input) && !scanner.hasNext()) {
//                    this.showAlertAddDialog(actionEvent, "Invalid Username!");
//                }
//            }
//
//        }


    }
}






