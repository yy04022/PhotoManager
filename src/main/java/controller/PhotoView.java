package controller;

import model.Album;
import model.Tag;
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
import model.User;

import java.io.*;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

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
        readTagText();
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
                if(!albumData.get(i).getImagePath().equals("null")&&albumData.get(i).getUser().equals(loginUser)) {
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
        tagsLV.getItems().clear();

        for (Tag tag : tagData) {
            if (filteredAlbumData.get(index).getImagePath().equals(tag.getImagePath())) {// checks each to find imagepath match
                tagsLV.getItems().add(tag.getTagType() + ", " + tag.getTagValue());
            }


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
            if(filteredTagData.get(i).getTagType().equals(type)&&filteredTagData.get(i).getTagValue().equals(value)){
                showAlertAddDialog(actionEvent,"Error! Duplicate tagValue and tagType!");
                duplicate =true;
                break;
            }
        }
        if(!type.isEmpty()&&!value.isEmpty()&&!duplicate){
            Tag newTag = new Tag(loginUser,album.getImagePath(),type,value);

            tagsLV.getItems().add(type+value);

            tagData.add(newTag);
            writeTagText();
            Date d = Calendar.getInstance().getTime();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
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

            writeTagText();
            readTagText();




//            Date d = Calendar.getInstance().getTime();
//            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//            String sd = dateFormat.format(d);
//            Album album = filteredAlbumData.get(index);
//            album.setDate(sd);
//            //System.out.println("Date after remove tag: "+album.getDate());
//            displayData(index);
//            writeText();
//            readTagText();
        }

    }

    public void onBackButtonClick(ActionEvent actionEvent) {
        loadPage("AlbumView.fxml","Album View",actionEvent);
    }

    public void onLogoutButtonClick(ActionEvent actionEvent) {
        loadPage("Login.fxml","Login",actionEvent);
    }
}
