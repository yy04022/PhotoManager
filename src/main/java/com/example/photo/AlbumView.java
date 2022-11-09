package com.example.photo;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class AlbumView extends Controller implements Initializable {


    public TextField photoNameTF;
    public TextField albumNameTF;
    public TextField captionNameTF;
    public ObservableList<Album> filiteredAlbumData = FXCollections.observableArrayList();
    public ImageView photoIV;
    public TextField locationTF;
    public GridPane photoGp;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

//        photoGp.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Album>() {
//
//
//            @Override
//            public void changed(ObservableValue<? extends Album> observable, Album oldValue, Album newValue) {
//                // Your action here
//                try {
//                    String albumName = newValue.getAlbumName();
//                    albumNameTF.setText(albumName);
//                    photoNameTF.setText(newValue.getImagePath());
//                    captionNameTF.setText(newValue.getCaption());
//                    displayImage(newValue.getImagePath());
//                } catch (NullPointerException e) {
//
//                }
//
//            }
//
//        });
        //todo;figure out why there's duplicate photo display
        for(int i = 0;i<albumData.size();i++){

            if(selectedAlbum.equals(albumData.get(i).getAlbumName())) {
                filiteredAlbumData.add(albumData.get(i));
            }

        }
        photoGp.setUserData(filiteredAlbumData);
    }
    public void displayImage(String imagePath){
        InputStream stream = null;
        try {
            stream = new FileInputStream(imagePath);
        }catch (FileNotFoundException e){
            System.out.println("File not found");
        }
        Image image = new Image(stream);
        photoIV.setImage(image);

    }

    public void addPhotoButtonClick(ActionEvent actionEvent) {
    }

    public void deletePhotoButtonClick(ActionEvent actionEvent) {
    }

    public void captionButtonClick(ActionEvent actionEvent) {
    }

    public void moveLocationButtonClick(ActionEvent actionEvent) {
    }

    public void copyLocationButtonlick(ActionEvent actionEvent) {
    }

    public void openPhotoButtonClick(ActionEvent actionEvent) {
    }


}
