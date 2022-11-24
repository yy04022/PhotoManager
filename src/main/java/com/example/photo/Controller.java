package com.example.photo;

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

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;

public class Controller {
    public static int photoIndex;

    public static String loginUser;
    public static String selectedAlbum;
    public static ObservableList<Album> albumData = FXCollections.observableArrayList();

    public ObservableList<Tag> tagData = FXCollections.observableArrayList();
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
    @FXML
    void showAlertAddDialog(ActionEvent event,String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Alert!");
        alert.setContentText(message);
        alert.show();


    }
    @FXML
    String showDialog(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert!");
        alert.setContentText("Are you sure to make this change?");
        Optional<ButtonType> result = alert.showAndWait();

        if(result.isEmpty()){
            return "cancel";
        } else if (result.get()== ButtonType.OK) {
            return "ok";
        } else if (result.get()== ButtonType.CANCEL) {
            return "cancel";

        }
        return "cancel";
    }
    public void writeText(){
        StringBuilder stringBuilder = new StringBuilder();
        for (Album album:albumData) {
            stringBuilder.append(album.getUser()).append(",")
                    .append(album.getAlbumName()).append(",")
                    .append(album.getImagePath()).append(",")
                    .append(album.getCaption()).append(",")
                    .append(album.getDate()).append("\n");
        }
        try {

            FileWriter fileWriter = new FileWriter("src/main/java/com/example/photo/album.txt",false);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(stringBuilder.toString());
            bufferedWriter.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void writePhotoText(){
        StringBuilder stringBuilder = new StringBuilder();
        for (Tag tag : tagData) {
            stringBuilder.append(tag.getUser()).append(",")
                    .append(tag.getImagePath()).append(",")
                    .append(tag.getTagType()).append(",")
                    .append(tag.getTagValue()).append("\n");
        }
        try {

            FileWriter fileWriter = new FileWriter("src/main/java/com/example/photo/photo.txt",false);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(stringBuilder.toString());
            bufferedWriter.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
