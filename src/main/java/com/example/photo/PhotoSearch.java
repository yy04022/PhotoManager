package com.example.photo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Scanner;

public class PhotoSearch extends Controller implements Initializable {
    public TextField tagTypeTF;
    public TextField tagValueTF;
    public GridPane resultGP;
    public DatePicker toDatePicker;
    public DatePicker fromDatePicker;
    boolean haveFromDate =false;
    boolean haveToDate = false;
    public ObservableList<String> resultImagePathArray = FXCollections.observableArrayList();

    public ObservableList<Album> filteredAlbumData = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        readTag();

    }
    public void readTag() {
        tagData.clear();
        File file = new File("src/main/java/com/example/photo/photo.txt");
        Scanner input = null;
        try {
            input = new Scanner(file);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        while(input.hasNext()){
            String line = input.nextLine();
            String [] array = line.split(",");
            String imagePath = array[1];
            String tagType = array[2];
            String tagValue = array[3];


            tagData.add(new Tag(loginUser,imagePath,tagType,tagValue));

        }
    }

    public void searchButtonClick(ActionEvent actionEvent) {
        String tagType = tagTypeTF.getText().toLowerCase();
        String tagValue = tagValueTF.getText().toLowerCase();
        if(haveFromDate&&haveToDate){
            //search by date

        } else if (!tagType.isEmpty()&&!tagValue.isEmpty()) {
            for(int i = 0; i<tagData.size();i++){
                String existingType = tagData.get(i).getTagType();
                String existingValue = tagData.get(i).getTagValue();
                if(tagType.equals(existingType)&&tagValue.equals(existingValue)){
                    resultImagePathArray.add(tagData.get(i).getImagePath());

                }
            }
        }else {
            showAlertAddDialog(actionEvent,"You need to select Dates or enter Tag to search photo!");
            return;
        }
        
        filterAlbum(resultImagePathArray);
        deleteDuplicateImage(filteredAlbumData);
        // TODO: 11/26/22 FindOutWhy only one picture displayed when they all match the search result 
        setAllThumbNail();
        filteredAlbumData.clear();
    }
    public void deleteDuplicateImage(ObservableList<Album> filteredAlbumData){
        for(int i = 0;i< filteredAlbumData.size();i++){
            for(int j =0;j<filteredAlbumData.size();j++){
                if(filteredAlbumData.get(j).getImagePath().equals(filteredAlbumData.get(i).getImagePath())){
                    filteredAlbumData.remove(filteredAlbumData.get(j));
                }
            }
        }
    }

    public void filterAlbum(ObservableList<String> resultImageData){
        for(int i =0 ; i<resultImageData.size();i++){
            for(int j =0; j<albumData.size();j++){
                if (albumData.get(j).getImagePath().equals(resultImageData.get(i))){
                    filteredAlbumData.add(albumData.get(j));
                }
            }
        }

    }

    public double getRow(double num){
        double row = num/3;
        return Math.ceil(row);

    }
    public double getCol(double num){
        double col = num%3;
        return col;
    }
    public void setAllThumbNail(){
        double thumbnailSize = filteredAlbumData.size();

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
    public void displayThumbNail(int i, int j, int count){
        String imagePath = filteredAlbumData.get(count).getImagePath();
        String caption = filteredAlbumData.get(count).getCaption();

        VBox vBox = setThumbnail(filteredAlbumData.get(count),imagePath, caption,count);

        resultGP.add(vBox, i,j);

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
            });
        }
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
    

    public void createFromResultButtonClick(ActionEvent actionEvent) {
        Album album = new Album(null, null, null, null, null);
        //add param to this ^
        Date d = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String sd = dateFormat.format(d);
        album.setDate(sd);
    }

    public void onBackButtonClick(ActionEvent actionEvent) {

        loadPage("UserDashboard.fxml","UserDashboard",actionEvent);
    }

    public void onLogoutButtonClick(ActionEvent actionEvent) {
        loginUser =null;

        loadPage("Login.fxml","Login",actionEvent);
    }

    public void getFromDate(ActionEvent actionEvent) {
        LocalDate fromDate = fromDatePicker.getValue();
        haveFromDate = true;

    }

    public void getToDate(ActionEvent actionEvent) {
        LocalDate toDate = fromDatePicker.getValue();
        haveToDate = true;

    }



    public void secondSearchButtonClick(ActionEvent actionEvent) {
    }


}
