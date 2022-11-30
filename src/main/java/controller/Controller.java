package controller;

import model.Album;
import model.Tag;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.Optional;

public class Controller {
    public static int photoIndex;

    public static String loginUser;
    public static String selectedAlbum;
    public static String serializeFile = "album.ser";
    public static String serializeUserFile = "user.ser";
    public static String serializeTagFile = "tag.ser";

    public static ObservableList<User> userData = FXCollections.observableArrayList();
    public static ObservableList<Album> albumData = FXCollections.observableArrayList();

    public static ObservableList<Tag> tagData = FXCollections.observableArrayList();

    public void loadPage(String fxmlPath, String title, ActionEvent actionEvent) {
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

    @FXML
    void showAlertAddDialog(ActionEvent event, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Alert!");
        alert.setContentText(message);
        alert.show();


    }

    @FXML
    String showDialog(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert!");
        alert.setContentText("Are you sure to make this change?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isEmpty()) {
            return "cancel";
        } else if (result.get() == ButtonType.OK) {
            return "ok";
        } else if (result.get() == ButtonType.CANCEL) {
            return "cancel";

        }
        return "cancel";
    }

    public void writeText() {

        try {
            ArrayList<Album> albumArrayList = new ArrayList<Album>();
            for (Album album : albumData) {
                albumArrayList.add(album);
            }
            FileOutputStream fileOutputStream = new FileOutputStream(serializeFile);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(albumArrayList);
            objectOutputStream.close();
            fileOutputStream.close();
            System.out.println("AlbumData serialized");
        } catch (IOException e) {
            e.printStackTrace();
            showAlertAddDialog(new ActionEvent(), "File Not Read");
        }


    }

//    public void writePhotoText() {
//        StringBuilder stringBuilder = new StringBuilder();
//        for (Tag tag : tagData) {
//            stringBuilder.append(tag.getUser()).append(",")
//                    .append(tag.getImagePath()).append(",")
//                    .append(tag.getTagType()).append(",")
//                    .append(tag.getTagValue()).append("\n");
//        }
//        try {
//
//            FileWriter fileWriter = new FileWriter("src/main/java/controller/photo.txt", false);
//            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
//            bufferedWriter.write(stringBuilder.toString());
//            bufferedWriter.close();
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public void readUserText() throws FileNotFoundException {


        try {
            FileInputStream fileInputStream = new FileInputStream(serializeUserFile);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            ArrayList<User> userArrayList = (ArrayList) objectInputStream.readObject();
            userData.clear();
            for (User user : userArrayList) {
                userData.add(user);
            }
            System.out.println("Userdata are read");
        } catch (IOException e) {
            e.printStackTrace();
            showAlertAddDialog(new ActionEvent(), "User Not Read");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeUserText() {

        try {
            ArrayList<User> userArrayList = new ArrayList<User>();
            for (User user : userData) {
                userArrayList.add(user);
            }
            FileOutputStream fileOutputStream = new FileOutputStream(serializeUserFile);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(userArrayList);
            objectOutputStream.close();
            fileOutputStream.close();
            System.out.println("user serialized");
        } catch (IOException e) {
            e.printStackTrace();
            showAlertAddDialog(new ActionEvent(), "File Not Read");
        }
    }
    public void writeTagText() {

        try {
            ArrayList<Tag> tagArrayList = new ArrayList<Tag>();
            for (Tag tag : tagData) {
                tagArrayList.add(tag);
            }
            FileOutputStream fileOutputStream = new FileOutputStream(serializeTagFile);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(tagArrayList);
            objectOutputStream.close();
            fileOutputStream.close();
            System.out.println("tag serialized");
        } catch (IOException e) {
            e.printStackTrace();
            showAlertAddDialog(new ActionEvent(), "File Not Read");
        }
    }
    public void readTagText(){

        try {
            FileInputStream fileInputStream = new FileInputStream(serializeTagFile);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            ArrayList<Tag> tagArrayList = (ArrayList) objectInputStream.readObject();
            tagData.clear();
            for (Tag tag : tagArrayList) {
                tagData.add(tag);
            }
            System.out.println("tag are read");
        } catch (IOException e) {
            e.printStackTrace();
            showAlertAddDialog(new ActionEvent(), "tag Not Read");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
