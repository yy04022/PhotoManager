package controller;

import model.Album;
import model.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

public class AdminController extends Controller {
    public TextField userNameTF;
    @FXML
    public ListView<User> userList;
    private int selectedUserIndex = 0;

    private final ObservableList<User> data = FXCollections.observableArrayList();
    public void initialize(URL url, ResourceBundle resourceBundle) {

        userList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<User>() {

            @Override
            public void changed(ObservableValue<? extends User> observable, User oldValue, User newValue) {
                // Your action here
                try {
                    String userName = newValue.getUsername();
                    userNameTF.setText(userName);

                    selectedUserIndex = userList.getSelectionModel().getSelectedIndex();

                } catch (NullPointerException e) {

                }

            }

        });
    }
    private void setupTable() {
        try {
            readUserText();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        userList.setItems(userData);


    }



    public void deleteUserButtonClick(ActionEvent actionEvent) {
        String confirmation=showDialog(actionEvent);
        if(confirmation.equals("ok")) {
            User deleteUser = userList.getSelectionModel().getSelectedItem();
            userList.getItems().remove(deleteUser);
            userData.remove(deleteUser);
            userList.getSelectionModel().select(deleteUser);
            writeUserText();

        }
    }

    public void listUserButtonClick(ActionEvent actionEvent) {
        userData.clear();
        setupTable();
    }

    public void addUserButtonClick(ActionEvent actionEvent) {
        String username = userNameTF.getText();
        User newUser = new User(username);
        String confirmation=showDialog(actionEvent);
        if(confirmation.equals("ok")&& !userNameTF.getText().isEmpty()) {
            if (userData.contains(newUser)) {
                System.out.println("duplicate user");
            } else {
                userData.add(newUser);
                userList.setItems(userData);
                userNameTF.clear();

                writeUserText();
            }
        }
    }

    public void onLogoutButtonClick(ActionEvent actionEvent) {
        loadPage("Login.fxml","Login",actionEvent);
    }
}
