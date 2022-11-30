package controller;

import model.Album;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.URL;
import java.util.*;

public class UserDashboard extends Controller implements Initializable {
    public TextField albumNameTF;


    private int selectedAlbumIndex = 0;
    @FXML
    public ListView<Album> albumList;

    public void initialize(URL url, ResourceBundle resourceBundle) {

        setupAlbum();

        albumList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Album>() {


            @Override
                public void changed(ObservableValue<? extends Album> observable, Album oldValue, Album newValue) {
                // Your action here
                try {
                    String albumName = newValue.getAlbumName();
                    albumNameTF.setText(albumName);
                    selectedAlbum = albumName;
                    selectedAlbumIndex = albumList.getSelectionModel().getSelectedIndex();

                } catch (NullPointerException e) {

                }

            }

        });
    }

    private void setupAlbum() {

        ObservableList<Album> filiteredAlbumData = FXCollections.observableArrayList();
        filiteredAlbumData.clear();
        albumData.clear();
        try {
            readText();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        HashSet<String> hashSet = new HashSet<String>();
        for(int i = 0;i<albumData.size();i++){
            boolean valid = hashSet.add(albumData.get(i).getAlbumName());
            if(valid&& albumData.get(i).getUser().equals(loginUser)) {
                filiteredAlbumData.add(albumData.get(i));
            }

        }
        System.out.println(albumData.size());
        albumList.setItems(filiteredAlbumData);


    }
    public void readText() throws FileNotFoundException {

        try {
            FileInputStream fileInputStream = new FileInputStream(serializeFile);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            ArrayList<Album> albumArrayList = (ArrayList)objectInputStream.readObject();
            albumData.clear();
            for(Album album: albumArrayList){
                albumData.add(album);
            }
            System.out.println(albumData.get(0).getAlbumName());
        } catch (IOException e) {
            e.printStackTrace();
            showAlertAddDialog(new ActionEvent(), "File Not Read");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

//        File file = new File("src/main/java/com/example/photo/album.txt");
//        Scanner input = new Scanner(file);
//
//        while(input.hasNext()){
//            String line = input.nextLine();
//            String [] array  = line.split(",");
//            Album newAlbum = new Album(array[0],array[1],array[2],array[3],array[4]);
//              albumData.add(newAlbum);
//        }
    }
//    public void writeText(Album album){
//        try {
//            FileWriter fileWriter = new FileWriter("src/main/java/com/example/photo/album.txt",true);
//            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
//            bufferedWriter.write(album.getUser()+"," +album.getAlbumName() + "," +album.getImagePath() +","+ album.getCaption());
//            bufferedWriter.newLine();
//            bufferedWriter.close();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//
//    }
    public void editText(){
        StringBuilder stringBuilder = new StringBuilder();
        for (Album album:albumData) {
            stringBuilder.append(album.getUser()).append(",")
                    .append(album.getAlbumName()).append(",")
                    .append(album.getImagePath()).append(",")
                    .append(album.getCaption()).append("\n");
        }
        try {

            FileWriter fileWriter = new FileWriter("src/main/java/controller/album.txt",false);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(stringBuilder.toString());
            bufferedWriter.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public void createAlbumButtonClick(ActionEvent actionEvent) {
        String username = loginUser;
        String albumName = albumNameTF.getText();
        boolean duplicate = false;
        Album newAlbum = new Album(username, albumName,"null","null", "No Date");
        for(Album album: albumData) {
           if(album.getAlbumName().equals(albumName)&&album.getUser().equals(loginUser) ){
               showAlertAddDialog(actionEvent,"Duplicate Album!");
               duplicate = true;
               break;
           }
        }
        if(!duplicate) {
            albumNameTF.clear();
            albumData.add(newAlbum);
            writeText();
            albumList.getItems().add(newAlbum);
        }

    }

    public void deleteAlbumButtonClick(ActionEvent actionEvent) {

        Album deleteAlbum = albumList.getSelectionModel().getSelectedItem();
        String deleteAlbumName = deleteAlbum.getAlbumName();
        ObservableList<Album> newList = FXCollections.observableArrayList();
        for(int i =0; i<albumData.size();i++){
            if(!albumData.get(i).getAlbumName().equals(deleteAlbumName)) {
                newList.add(albumData.get(i));
            }
        }
        albumData = newList;
        int selectedItemIndex = albumList.getSelectionModel().getSelectedIndex();
        albumList.getItems().remove(selectedItemIndex);
        writeText();

    }

    public void editAlbumButtonClick(ActionEvent actionEvent) {
        String newAlbumName = albumNameTF.getText();
        String editAlbumName = albumList.getSelectionModel().getSelectedItem().getAlbumName();
        Album editedAlbum = null;
        for(int i =0; i<albumData.size();i++){
            if(albumData.get(i).getAlbumName().equals(editAlbumName)) {
                albumData.get(i).setAlbumName(newAlbumName);
                editedAlbum = albumData.get(i);
            }
        }
        int selectedItemIndex = albumList.getSelectionModel().getSelectedIndex();
        albumList.getItems().remove(selectedItemIndex);
        albumList.getItems().add(editedAlbum);


        writeText();
    }

    public void openAlbumButtonClick(ActionEvent actionEvent) {
        //get selected index
        try {
            albumList.getSelectionModel().getSelectedItem().getAlbumName();
            loadPage("AlbumView.fxml","Album View",actionEvent);
        } catch (NullPointerException e){
            System.out.println("Item not selected");
        }

    }

    public void onLogoutButtonClick(ActionEvent actionEvent) {
        loginUser =null;

        loadPage("Login.fxml","Login",actionEvent);
    }

    public void photoSearchButtonClick(ActionEvent actionEvent) {

        loadPage("PhotoSearch.fxml","PhotoSearch",actionEvent);

    }
}
