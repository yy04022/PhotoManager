package com.example.photo;

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
            readText();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        userList.setItems(data);


    }
    public void readText() throws FileNotFoundException {
        File file = new File("src/main/java/com/example/photo/users.txt");
        Scanner input = new Scanner(file);
        while(input.hasNext()){
            String line = input.next();
            User user = new User(line);
            data.add(user);

        }
    }

    public void writeText(User user){
        try {
            FileWriter fileWriter = new FileWriter("src/main/java/com/example/photo/users.txt",true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.newLine();
            bufferedWriter.write(user.getUsername());
            bufferedWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
    public void deleteUserButtonClick(ActionEvent actionEvent) {
        User deleteUser = userList.getSelectionModel().getSelectedItem();
        userList.getItems().remove(deleteUser);
        data.remove(deleteUser);
       userList.getSelectionModel().select(deleteUser);
        StringBuilder stringBuilder = new StringBuilder();
        for (User user : data) {
            stringBuilder.append(user.getUsername()).append("\n");
        }
        try {

            FileWriter fileWriter = new FileWriter("src/main/java/com/example/photo/users.txt", false);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(stringBuilder.toString());
            bufferedWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void listUserButtonClick(ActionEvent actionEvent) {

        setupTable();
    }

    public void addUserButtonClick(ActionEvent actionEvent) {
        String username = userNameTF.getText();
        User newUser = new User(username);
        if(data.contains(newUser)){
            System.out.println("duplicate user");
        }else{
            data.add(newUser);
            userList.setItems(data);
            userNameTF.clear();

            writeText(newUser);
        }

    }

    public void onLogoutButtonClick(ActionEvent actionEvent) {
        loadPage("Login.fxml","Login",actionEvent);
    }
}
