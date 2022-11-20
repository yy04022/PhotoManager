package com.example.photo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.scene.image.Image;

import java.io.*;
import java.net.URL;
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
    public int index = photoIndex;

    public ListView<String> listOfTags;


    public void initialize(URL url, ResourceBundle resourceBundle){
        filterData();
        displayImage(photoIndex);
        displayData(photoIndex);
        readTag();
        //listView.setItems(listOfTags);
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

    public void writeTag(){
        StringBuilder stringBuilder = new StringBuilder();

            stringBuilder.append(filteredAlbumData.get(index).getUser()).append(",")
                    .append(filteredAlbumData.get(index).getImagePath()).append(",")
                    .append(filteredAlbumData.get(index).getTagType()).append(",")
                    .append(filteredAlbumData.get(index).getTagValue()).append("\n");

        try{
            FileWriter fileWriter = new FileWriter("src/main/java/com/example/photo/photo.txt",false);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(stringBuilder.toString());
            bufferedWriter.close();
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    public void readTag() {
        listOfTags.getItems().clear();
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
            if(filteredAlbumData.get(index).getImagePath().equals(array[1])){// checks each to find imagepath match
                listOfTags.getItems().add(array[2]+", "+array[3]);
            }
        }
    }

    public void setNextPhotoButtonClick(ActionEvent actionEvent) {
        if(index<filteredAlbumData.size()-1){
            index++;
            displayImage(index);
            displayData(index);
            listOfTags.getItems().clear();
            readTag();
        }
    }

    public void setPrevPhotoButtonClick(ActionEvent actionEvent) {
        if(index!=0){
            index--;
            displayImage(index);
            displayData(index);
            listOfTags.getItems().clear();
            readTag();
        }
    }

    public void onAddTagButtonClick(ActionEvent actionEvent) {
        String type = tageTypeTF.getText();
        String value = tagValueTF.getText();
        if(!type.isEmpty()&&!value.isEmpty()){
            Album album = filteredAlbumData.get(index);
            album.setTagType(type);
            album.setTagValue(value);
            listOfTags.getItems().add(filteredAlbumData.get(index).getTagType()+filteredAlbumData.get(index).getTagValue());
            try {
                FileWriter fileWriter = new FileWriter("src/main/java/com/example/photo/photo.txt",true);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(album.getUser()+","+album.getImagePath()+","+album.getTagType()+","+album.getTagValue());
                bufferedWriter.newLine();
                bufferedWriter.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

//        if(filteredAlbumData.get(index).getTagType()==null
//                && filteredAlbumData.get(index).getTagValue()==null){
//            filteredAlbumData.get(index).setTagType(type);
//            filteredAlbumData.get(index).setTagValue(value);
//            System.out.println("FIRST TAG");
//         }
//        else{ //second+ tags
//            Album newAlbum = new Album(filteredAlbumData.get(index).getUser(),
//                                    filteredAlbumData.get(index).getAlbumName(),
//                                    filteredAlbumData.get(index).getImagePath(),
//                                    filteredAlbumData.get(index).getCaption(),
//                                    filteredAlbumData.get(index).getDate());
//            newAlbum.setTagType(type);
//            newAlbum.setTagValue(value);
//            albumData.add(newAlbum);
//            System.out.println("SECOND TAG");
////        }
//        writeTag();
//        filterData();
        readTag();
        tageTypeTF.clear();
        tagValueTF.clear();

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
