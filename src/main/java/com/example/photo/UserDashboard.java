package com.example.photo;

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


    private int selectedUserIndex = 0;
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
                    selectedUserIndex = albumList.getSelectionModel().getSelectedIndex();

                } catch (NullPointerException e) {

                }

            }

        });
    }

    private void setupAlbum() {
        try {
            readText();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        ObservableList<Album> filiteredAlbumData = FXCollections.observableArrayList();
        HashSet<String> hashSet = new HashSet<String>();
        for(int i = 0;i<albumData.size();i++){
            boolean valid = hashSet.add(albumData.get(i).getAlbumName());
            if(valid) {
                filiteredAlbumData.add(albumData.get(i));
            }

        }
        System.out.println(filiteredAlbumData.size());
        albumList.setItems(filiteredAlbumData);


    }
    public void readText() throws FileNotFoundException {
        File file = new File("src/main/java/com/example/photo/album.txt");
        Scanner input = new Scanner(file);

        while(input.hasNext()){
            String line = input.nextLine();
            String [] array  = line.split(",");
            Album newAlbum = new Album(array[0],array[1],array[2],array[3]);

          if(newAlbum.getUser().equals(loginUser)) {
              albumData.add(newAlbum);

//              boolean valid = hashSet.add(array[1]);
//              if(valid) {
//                  albumData.add(newAlbum);
//              }
            }

        }
    }
    public void writeText(Album album){
        try {
            FileWriter fileWriter = new FileWriter("src/main/java/com/example/photo/album.txt",true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(album.getUser()+"," +album.getAlbumName() + "," +album.getImagePath() +","+ album.getCaption());
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
    public void createAlbumButtonClick(ActionEvent actionEvent) {
        String username = loginUser;
        String albumName = albumNameTF.getText();
        Album newAlbum = new Album(username, albumName,"null","null");

            albumData.add(newAlbum);
            albumList.setItems(albumData);
            albumNameTF.clear();
            writeText(newAlbum);

    }

    public void deleteAlbumButtonClick(ActionEvent actionEvent) {

        Album deleteAlbum = albumList.getSelectionModel().getSelectedItem();
        albumList.getItems().remove(deleteAlbum);
        albumData.remove(deleteAlbum);
        albumList.getSelectionModel().select(deleteAlbum);
        StringBuilder stringBuilder = new StringBuilder();
        for (Album album : albumData) {
            stringBuilder.append(album.getAlbumName()).append("\n");
        }
        try {

            FileWriter fileWriter = new FileWriter("src/main/java/com/example/photo/album.txt", false);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(stringBuilder.toString());
            bufferedWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void editAlbumButtonClick(ActionEvent actionEvent) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Album album: albumData) {
            stringBuilder.append(album.getUser()).append(",")
                    .append(album.getUser()).append("\n");
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
        loadPage("Login.fxml","Login",actionEvent);
    }

    public void photoSearchButtonClick(ActionEvent actionEvent) {

        loadPage("PhotoSearch.fxml","PhotoSearch",actionEvent);

    }
}
