package com.example.photo;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Album {

    SimpleStringProperty user = new SimpleStringProperty();
    SimpleStringProperty albumName = new SimpleStringProperty();
    SimpleStringProperty imagePath = new SimpleStringProperty();
    SimpleStringProperty caption= new SimpleStringProperty();
    SimpleStringProperty date = new SimpleStringProperty();
    SimpleStringProperty tagType = new SimpleStringProperty();
    SimpleStringProperty tagValue = new SimpleStringProperty();
    public Album(String user, String albumName,String imagePath, String caption, String date){
        setUser(user);
        setAlbumName(albumName);
        setImagePath(imagePath);
        setCaption(caption);
        setDate(date);
    }

    //ObservableList<String> listOfTags = FXCollections.observableArrayList();

    //public ObservableList getTagList(){
    //    return listOfTags;
    //}

    //public void setTags(String s){
    //    this.listOfTags.add(s);
    //}

    public String getTagType(){
        return tagType.get();
    }

    public void setTagType(String t){
        this.tagType.set(t);
    }

    public String getTagValue(){
        return tagValue.get();
    }

    public void setTagValue(String t){
        this.tagValue.set(t);
    }

    public String getDate(){
        return date.get();
    }

    public void setDate(String sd){
        this.date.set(sd);
    }

    public String getCaption() {
        return caption.get();
    }

    public SimpleStringProperty captionProperty() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption.set(caption);
    }

    public String getImagePath() {
        return imagePath.get();
    }

    public SimpleStringProperty imagePathProperty() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath.set(imagePath);
    }

    public String getAlbumName() {
        return albumName.get();
    }

    public SimpleStringProperty albumNameProperty() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName.set(albumName);
    }



    public String getUser() {
        return user.get();
    }

    public SimpleStringProperty userProperty() {
        return user;
    }

    public void setUser(String user) {
        this.user.set(user);
    }

    @Override
    public String toString() {
        return  getAlbumName() ;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Album album = (Album) o;
//        return getUser().equals(album.getUser())&& getAlbumName().equals(album.getAlbumName());
//    }



}
