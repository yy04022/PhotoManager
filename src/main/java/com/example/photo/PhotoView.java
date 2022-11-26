package com.example.photo;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

import java.io.*;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Scanner;

//import javax.management.RuntimeErrorException;

public class PhotoView extends Controller implements Initializable{

    public TextField tageTypeTF;
    public TextField tagValueTF;
    public ImageView photoIV;

    public Label photoNameLabel;
    public Label captionLabel;
    public Label dateLabel;

    public ObservableList<Album> filteredAlbumData = FXCollections.observableArrayList();
    public ObservableList<Tag> filteredTagData = FXCollections.observableArrayList();
    public int index = photoIndex;
    public int selectedTagIndex = -1;

    public ListView<String> tagsLV;


    public void initialize(URL url, ResourceBundle resourceBundle){
        filterData();
        displayImage(photoIndex);
        displayData(photoIndex);
        readTag();
        //listView.setItems(listOfTags);
        tagsLV.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                try {

                    selectedTagIndex = tagsLV.getSelectionModel().getSelectedIndex();

                } catch (NullPointerException e) {

                }
            }
        });
    }

    private void filterData(){
        filteredAlbumData.clear();
        for(int i = 0;i<albumData.size();i++){
            if(selectedAlbum.equals(albumData.get(i).getAlbumName())) {
                if(!albumData.get(i).getImagePath().equals("null")) {
                    filteredAlbumData.add(albumData.get(i));
                }
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

//    public void writeTag(){
//        StringBuilder stringBuilder = new StringBuilder();
//        for(Album album:filteredAlbumData) {
//            stringBuilder.append(filteredAlbumData.get(index).getUser()).append(",")
//                    .append(filteredAlbumData.get(index).getImagePath()).append(",")
//                    .append(filteredAlbumData.get(index).getTagType()).append(",")
//                    .append(filteredAlbumData.get(index).getTagValue()).append("\n");
//        }
//        try{
//            FileWriter fileWriter = new FileWriter("src/main/java/com/example/photo/photo.txt",false);
//            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
//            bufferedWriter.write(stringBuilder.toString());
//            bufferedWriter.close();
//        }catch(IOException e){
//            throw new RuntimeException(e);
//        }
//    }

    public void readTag() {
        tagData.clear();
        tagsLV.getItems().clear();
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
            if(filteredAlbumData.get(index).getImagePath().equals(imagePath)){// checks each to find imagepath match
                tagsLV.getItems().add(tagType+", "+tagValue);

            }

            tagData.add(new Tag(loginUser,imagePath,tagType,tagValue));

        }
    }


    public void setNextPhotoButtonClick(ActionEvent actionEvent) {
        if(index<filteredAlbumData.size()-1){
            index++;
            displayImage(index);
            displayData(index);
            tagsLV.getItems().clear();
            readTag();
        }
    }

    public void setPrevPhotoButtonClick(ActionEvent actionEvent) {
        if(index!=0){
            index--;
            displayImage(index);
            displayData(index);
            tagsLV.getItems().clear();
            readTag();
        }
    }
    public void filterTag(Album album){
        filteredTagData.clear();
        for(int i = 0;i<tagData.size();i++){
            if(tagData.get(i).getImagePath().equals(album.getImagePath())){
                filteredTagData.add(tagData.get(i));
            }
        }
    }

    public void onAddTagButtonClick(ActionEvent actionEvent) {
        String type = tageTypeTF.getText().toLowerCase();
        String value = tagValueTF.getText().toLowerCase();
        Album album = filteredAlbumData.get(index);
        filterTag(album);
        boolean duplicate = false;
        for(int i = 0; i<filteredTagData.size();i++){
            if(filteredTagData.get(i).getTagType().equals(type)){
                System.out.println("same tag");
                duplicate =true;
                break;
            }
        }
        if(!type.isEmpty()&&!value.isEmpty()&&!duplicate){

            tagsLV.getItems().add(type+value);
            //this filewriter works for adding but not for deltebutton click
            try {
                FileWriter fileWriter = new FileWriter("src/main/java/com/example/photo/photo.txt",true);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(album.getUser()+","+album.getImagePath()+","+type+","+value);
                bufferedWriter.newLine();
                bufferedWriter.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Date d = Calendar.getInstance().getTime();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
            String sd = dateFormat.format(d);
            album.setDate(sd);
            writeText();
            //System.out.println("Date after add tag: "+album.getDate());
        }
        displayData(index);

        readTag();
//        tagData.add(new Tag(loginUser,album.getImagePath(),type,value));
        tageTypeTF.clear();
        tagValueTF.clear();

    }

    public void onDeleteButtonClick(ActionEvent actionEvent) {
        if(selectedTagIndex!= -1){
            System.out.println("delete"+selectedTagIndex);
            tagsLV.getItems().remove(selectedTagIndex);
            tagData.remove(selectedTagIndex);
            writePhotoText();


//            todo, rewite writetext method,
//             becuase for loop of filifterAblumData contain all photo,
//             also do

            Date d = Calendar.getInstance().getTime();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
            String sd = dateFormat.format(d);
            Album album = filteredAlbumData.get(index);
            album.setDate(sd);
            //System.out.println("Date after remove tag: "+album.getDate());
            displayData(index);
            writeText();
        }

    }

    public void onBackButtonClick(ActionEvent actionEvent) {
        loadPage("AlbumView.fxml","Album View",actionEvent);
    }

    public void onLogoutButtonClick(ActionEvent actionEvent) {
        loadPage("Login.fxml","Login",actionEvent);
    }
}
