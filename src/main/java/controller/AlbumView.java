package controller;

import model.Album;
import model.Tag;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.*;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Scanner;

public class AlbumView extends Controller implements Initializable {



    public TextField captionNameTF;
    public ObservableList<Album> filiteredAlbumData = FXCollections.observableArrayList();

    public TextField locationTF;
    public GridPane photoGp;
    public AnchorPane albumAnchorPane;
    public Label albumNameTF;
    public Label photoNameTF;
    int selectedThumbNailIndex;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        setStockPhoto();
            photoGp.getChildren().clear();
            filiteredAlbumData.clear();
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
    private  void setStockPhoto(){
        albumData.get(0).setImagePath("data/stock/apple.jpeg");
        albumData.get(1).setImagePath("data/stock/cat.jpeg");
        albumData.get(2).setImagePath("data/stock/car.jpeg");
        albumData.get(3).setImagePath("data/stock/banana.jpeg");
        albumData.get(4).setImagePath("data/stock/chickfila.jpeg");
        albumData.get(5).setImagePath("data/stock/dog.jpeg");
        albumData.get(6).setImagePath("data/stock/apple.jpeg");
        albumData.get(7).setImagePath("data/stock/banana.jpeg");
        writeText();

    }
    private void filiterData(){
        filiteredAlbumData.clear();
        for(int i = 0;i<albumData.size();i++){

            if(selectedAlbum.equals(albumData.get(i).getAlbumName())) {
                if(!albumData.get(i).getImagePath().equals("null")&&albumData.get(i).getUser().equals(loginUser)) {
                    filiteredAlbumData.add(albumData.get(i));
                }
            }

        }

    }
    public void setAllThumbNail(){
        double thumbnailSize = filiteredAlbumData.size();

        if(thumbnailSize>9){
            thumbnailSize= 9;
        }
        double row= getRow(thumbnailSize);

        double col = getCol(thumbnailSize);

        int count=0;
        for(int i = 0; i<row;i++){

            if(count == 9){
                break;
            }
            if(i<row-1 || thumbnailSize%3 ==0){
                for(int j =0;j< 3;j++){
                    displayThumbNail(i,j,count);
                    count++;
                }
            } else{
                for(int j =0;j<col;j++){
                    displayThumbNail(i,j,count);
                    count++;
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
                photoNameTF.setText(imagePath);
                captionNameTF.setText(caption);

                selectedThumbNailIndex = this.index;
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
        String confirmation=showDialog(actionEvent);
        if(confirmation.equals("ok")) {
            String filePath = getUserFilePath();
            Album newAlbum = new Album(loginUser, selectedAlbum, filePath, "null", "null");
            Date d = Calendar.getInstance().getTime();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String sd = dateFormat.format(d);
            newAlbum.setDate(sd);
            albumData.add(newAlbum);
            writeText();
            refresh();
        }
    }

    public void deletePhotoButtonClick(ActionEvent actionEvent) {
        if(selectedThumbNailIndex!= -1){

            Album deleteAlbum = filiteredAlbumData.get(selectedThumbNailIndex);
            System.out.println("delte"+deleteAlbum);
            albumData.remove(deleteAlbum);
            deleteTagWhenNoPhoto(deleteAlbum);
            writeText();
            refresh();
        }
    }

    public  void deleteTagWhenNoPhoto( Album deleteAlbum){
        readTag();
        System.out.println("tagdata size"+tagData.size());
        for(Tag tag : tagData){
            for(Album album : filiteredAlbumData) {
                if (deleteAlbum.getImagePath().equals(tag.getImagePath())&&album.getUser().equals(tag.getUser())) {
                    if (!album.getImagePath().contains(tag.getImagePath())){
                        tagData.remove(tag);

                        writeTagText();
                    }
                }
            }
        }

    }
    public void readTag() {
        tagData.clear();
//        File file = new File("src/main/java/controller/photo.txt");
//        Scanner input = null;
//        try {
//            input = new Scanner(file);
//        } catch (FileNotFoundException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        while(input.hasNext()){
//            String line = input.nextLine();
//            String [] array = line.split(",");
//            String imagePath = array[1];
//            String tagType = array[2];
//            String tagValue = array[3];
//
//
//            tagData.add(new Tag(loginUser,imagePath,tagType,tagValue));
//
//        }
    }

    public void captionButtonClick(ActionEvent actionEvent) {
        if(selectedThumbNailIndex!=-1){
            String caption = captionNameTF.getText();
            Album editAlbum = filiteredAlbumData.get(selectedThumbNailIndex);
            editAlbum.setCaption(caption);

            for(int i = 0; i<albumData.size();i++){
                if(albumData.get(i).getImagePath().equals(editAlbum.getImagePath())&&albumData.get(i).getUser().equals(loginUser)){
                    albumData.get(i).setCaption(caption);
                }
            }
            Date d = Calendar.getInstance().getTime();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String sd = dateFormat.format(d);
            editAlbum.setDate(sd);
            writeText();
            refresh();
            selectedThumbNailIndex = -1;
        }

    }

    public void moveLocationButtonClick(ActionEvent actionEvent) {


        if(selectedThumbNailIndex!=-1 && !locationTF.getText().isEmpty()) {
            String destinationLocation = locationTF.getText();
            Album deleteAlbum = filiteredAlbumData.get(selectedThumbNailIndex);
            String newAlbumName = destinationLocation;
            String newAlbumUser = deleteAlbum.getUser();
            String newAlbumImagePath = deleteAlbum.getImagePath();
            String newAlbumCaption = deleteAlbum.getCaption();
            String newAlbumDate = deleteAlbum.getDate();
            Album newAlbum = new Album(newAlbumUser,newAlbumName,newAlbumImagePath,newAlbumCaption, newAlbumDate);

            boolean albumCreated = false;

            for (int i = 0; i < albumData.size(); i++) {
                if(albumData.get(i).getAlbumName().equals(destinationLocation)&&albumData.get(i).getUser().equals(loginUser)) {
                    albumCreated = true;
                }
            }
            if(albumCreated){
                albumData.remove(deleteAlbum);
                albumData.add(newAlbum);
                writeText();
                refresh();

            } else if (!albumCreated) {
                locationTF.clear();
                showAlertAddDialog(actionEvent,"This Album doesn't exist!");
            }
            Date d = Calendar.getInstance().getTime();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String sd = dateFormat.format(d);
            newAlbum.setDate(sd);

        } else if (selectedThumbNailIndex==-1 && locationTF.getText().isEmpty()) {
            showAlertAddDialog(actionEvent, "You need to select a photo and enter a album name!");
        }else if (selectedThumbNailIndex==-1) {
            showAlertAddDialog(actionEvent,"You need to select a photo!");
        }
    }

    public void copyLocationButtonlick(ActionEvent actionEvent) {
        if(selectedThumbNailIndex!=-1 && !locationTF.getText().isEmpty()) {
            String destinationLocation = locationTF.getText();
            String user = loginUser;
            String albumName = destinationLocation;
            String imagePath = filiteredAlbumData.get(selectedThumbNailIndex).getImagePath();
            String caption = filiteredAlbumData.get(selectedThumbNailIndex).getCaption();
            String date = filiteredAlbumData.get(selectedThumbNailIndex).getDate();
            Album newAlbum = new Album(user, albumName, imagePath, caption, date);
           boolean albumCreated = false;

            for (int i = 0; i < albumData.size(); i++) {
                if(albumData.get(i).getAlbumName().equals(destinationLocation)&&albumData.get(i).getUser().equals(loginUser)) {
                    albumCreated = true;
                }
            }
            if(albumCreated== true){
                albumData.add(newAlbum);
                writeText();
                System.out.println(newAlbum.getUser() + newAlbum.getAlbumName() + newAlbum.getImagePath() + newAlbum.getCaption());
                refresh();
            }else if (!albumCreated) {
                locationTF.clear();
                showAlertAddDialog(actionEvent,"This Album doesn't exist!");
            }


        } else if (selectedThumbNailIndex==-1 && locationTF.getText().isEmpty()) {
            showAlertAddDialog(actionEvent,"You need to select a photo and enter a album name!");
        } else if (selectedThumbNailIndex==-1) {
            showAlertAddDialog(actionEvent,"You need to select a photo!");
        }

    }

    public void openPhotoButtonClick(ActionEvent actionEvent) {
        photoIndex = selectedThumbNailIndex;
        loadPage("PhotoView.fxml","PhotoView",actionEvent);
    }


    public void backButtonClick(ActionEvent actionEvent) {
//        selectedAlbum = null ;
        filiteredAlbumData.clear();
        albumData.clear();
        photoGp.getChildren().clear();

        loadPage("UserDashboard.fxml","UserDashboard",actionEvent);
    }

    public void logoutButtonClick(ActionEvent actionEvent) {

        loadPage("Login.fxml","Login",actionEvent);

    }
}
