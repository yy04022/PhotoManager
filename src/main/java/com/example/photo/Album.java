package com.example.photo;

import javafx.beans.property.SimpleStringProperty;

public class Album {

    SimpleStringProperty user = new SimpleStringProperty();
    SimpleStringProperty albumName = new SimpleStringProperty();
    SimpleStringProperty imagePath = new SimpleStringProperty();
    SimpleStringProperty caption= new SimpleStringProperty();
    public Album(String user, String albumName,String imagePath, String caption){
        setUser(user);
        setAlbumName(albumName);
        setImagePath(imagePath);
        setCaption(caption);
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
