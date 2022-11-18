package com.example.photo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class PhotoView extends Controller implements Initializable{

    public TextField tageTypeTF;
    public TextField tagValueTF;
    public ImageView photoIV;

    public Label photoNameLabel;
    public Label captionLabel;
    public Label dateLabel;

    public ObservableList<Album> filteredAlbumData = FXCollections.observableArrayList();
    public int index = photoIndex;

    public ListView<String> listOfTags;

    //private ObservableList<String> listOfTags = FXCollections.observableArrayList();
    //public ListView<String> listView = new ListView<String>(listOfTags);
    //public ListView<String> listView = new ListView<String>();

    public void initialize(URL url, ResourceBundle resourceBundle){
        filterData();
        displayImage(photoIndex);
        displayData(photoIndex);
        //listView.setItems(listOfTags);
    }

    private void filterData(){
        filteredAlbumData.clear();
        for(int i = 0;i<albumData.size();i++){
            if(selectedAlbum.equals(albumData.get(i).getAlbumName())) {
                filteredAlbumData.add(albumData.get(i));
            }

        }

    }

    public void displayImage(int num) {
        String imagePath = filteredAlbumData.get(num).getImagePath();
        InputStream stream = null;
        try {
            stream = new FileInputStream(imagePath);
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
        Image image = new Image(stream);
        photoIV.setImage(image);
    }

    public void displayData(int num){
        captionLabel.setText(filteredAlbumData.get(num).getCaption());
        dateLabel.setText(filteredAlbumData.get(num).getDate());
    }

    public void displayTags(int num){

    }

    public void saveTag(int num){
        
    }

    public void setNextPhotoButtonClick(ActionEvent actionEvent) {
        if(index<filteredAlbumData.size()-1){
            index++;
            displayImage(index);
            displayData(index);
        }
    }

    public void setPrevPhotoButtonClick(ActionEvent actionEvent) {
        if(index!=0){
            index--;
            displayImage(index);
            displayData(index);
        }
    }

    public void onAddTagButtonClick(ActionEvent actionEvent) {
        String type = tageTypeTF.getText();
        String value = tagValueTF.getText();
        String tag = type+", "+value;
        listOfTags.getItems().add(tag);
        //listView.setItems(listOfTags);
        //listView.getItems().add(type+", "+value);
    }

    public void onDeleteButtonClick(ActionEvent actionEvent) {
    }

    public void onBackButtonClick(ActionEvent actionEvent) {
        loadPage("AlbumView.fxml","Album View",actionEvent);
    }

    public void onLogoutButtonClick(ActionEvent actionEvent) {
        loadPage("Login.fxml","Login",actionEvent);
    }
}
