package com.example.photo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class AlbumView extends Controller implements Initializable {


    public TextField photoNameTF;
    public TextField albumNameTF;
    public TextField captionNameTF;
    public ObservableList<Album> filiteredAlbumData = FXCollections.observableArrayList();

    public TextField locationTF;
    public GridPane photoGp;
    public AnchorPane albumAnchorPane;
    int selectedThumbNailIndex;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            selectedThumbNailIndex = -1;
            albumNameTF.setText(selectedAlbum);

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


        photoGp.getChildren().clear();
        filiterData();
        setAllThumbNail();

    }
    private void filiterData(){
        filiteredAlbumData.clear();
        for(int i = 0;i<albumData.size();i++){

            if(selectedAlbum.equals(albumData.get(i).getAlbumName())) {
                filiteredAlbumData.add(albumData.get(i));
            }

        }

    }
    public void setAllThumbNail(){
        double thumbnailSize = filiteredAlbumData.size();
        if(thumbnailSize>9){
            thumbnailSize= 9;
        }
        System.out.println("thumbnailsize:"+thumbnailSize);
        double row= getRow(thumbnailSize);
        System.out.println("row"+row);
        double col = getCol(thumbnailSize);
        System.out.println("col"+col);
        int count=0;
        for(int i = 0; i<row;i++){
            System.out.println("count:"+count);
            if(count == 9){
                break;
            }
            if(i<row-1 || thumbnailSize%3 ==0){
                for(int j =0;j< 3;j++){
                    displayThumbNail(i,j,count);
                    count++;
                    System.out.println("if"+i + "," + j);
                }
            } else{
                for(int j =0;j<col;j++){
                    displayThumbNail(i,j,count);
                    count++;
                    System.out.println("if"+i + "," + j);
                }
            }
        }
    }
    public void refresh(){

        photoGp.getChildren().clear();
        filiterData();
        setAllThumbNail();
    }
    public double getRow(double num){
        double row = num/3;
        System.out.println("before ceil"+row);
        System.out.println("after ceil"+ Math.ceil(row));
        return Math.ceil(row);

    }
    public double getCol(double num){
        double col = num%3;
        return col;
    }
    public VBox setThumbnail(Album album,String imagePath, String caption,int index) {
//        VBox thumbnailVbox = new VBox();
        ThumbNail thumbnailVbox = new ThumbNail(album,index);
        ImageView imageView = new ImageView();

        Label label = new Label();

        imageView.setFitHeight(70);
        imageView.setFitWidth(100);
        imageView.setImage(displayImage(imagePath));

        label.setText(caption);
        thumbnailVbox.getChildren().addAll(imageView,label);
        return thumbnailVbox;

    }
    class ThumbNail extends VBox{
        String user;
        String albumName;
        String imagePath;
        String caption;
        int index;
        public ThumbNail(Album album,int index){
            user = album.getUser();
            albumName =album.getAlbumName();
            imagePath = album.getImagePath();
            caption = album.getCaption();
            this.index = index;
            setOnMouseClicked(e->{
                selectedThumbNailIndex = this.index;
                System.out.println(selectedThumbNailIndex);
            });
        }
    }
    public void displayThumbNail(int i, int j, int count){
        String imagePath = filiteredAlbumData.get(count).getImagePath();
        String caption = filiteredAlbumData.get(count).getCaption();

        VBox vBox = setThumbnail(filiteredAlbumData.get(count),imagePath, caption,count);

        photoGp.add(vBox, i,j);

    }


    public Image displayImage(String imagePath){
        InputStream stream = null;
        try {
            stream = new FileInputStream(imagePath);
        }catch (FileNotFoundException e){
            System.out.println("File not found");
        }

        Image image = new Image(stream);
        return image;

    }
    public String getUserFilePath(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Upload File Path");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("ALL FILES", "*.*"),
                new FileChooser.ExtensionFilter("ZIP", "*.zip"),
                new FileChooser.ExtensionFilter("PDF", "*.pdf"),
                new FileChooser.ExtensionFilter("TEXT", "*.txt"),
                new FileChooser.ExtensionFilter("IMAGE FILES", "*.jpg", "*.png", "*.gif")
        );

        File file = fileChooser.showOpenDialog(albumAnchorPane.getScene().getWindow());

        if (file != null) {
            System.out.println(file.getPath());
            return file.getPath();
        } else  {
            System.out.println("error"); // or something else
            return "";
        }
    }

    public void addPhotoButtonClick(ActionEvent actionEvent) {
        String filePath = getUserFilePath();
        Album newAlbum = new Album(loginUser,selectedAlbum,filePath,"null");
        albumData.add(newAlbum);
        writeText();
        refresh();

    }

    public void deletePhotoButtonClick(ActionEvent actionEvent) {
        if(selectedThumbNailIndex!= -1){

            Album deleteAlbum = filiteredAlbumData.get(selectedThumbNailIndex);
            System.out.println("delte"+deleteAlbum);
            albumData.remove(deleteAlbum);
            writeText();
            refresh();
        }
    }

    public void captionButtonClick(ActionEvent actionEvent) {
    }

    public void moveLocationButtonClick(ActionEvent actionEvent) {
    }

    public void copyLocationButtonlick(ActionEvent actionEvent) {
    }

    public void openPhotoButtonClick(ActionEvent actionEvent) {
    }


    public void backButtonClick(ActionEvent actionEvent) {
        selectedAlbum = null ;
        filiteredAlbumData.clear();
        albumData.clear();
        loadPage("UserDashboard.fxml","UserDashboard",actionEvent);
    }

    public void logoutButtonClick(ActionEvent actionEvent) {

        loadPage("Login.fxml","Login",actionEvent);

    }
}
