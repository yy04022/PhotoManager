package com.example.photo;

import javafx.beans.property.SimpleStringProperty;

public class Tag {
    SimpleStringProperty tagType = new SimpleStringProperty();
    SimpleStringProperty user = new SimpleStringProperty();
    SimpleStringProperty imagePath = new SimpleStringProperty();
    SimpleStringProperty tagValue = new SimpleStringProperty();
    public Tag(String username, String imagePath,String tagType,String tagValue){
        setUser(username);
        setImagePath(imagePath);
        setTagType(tagType);
        setTagValue(tagValue);

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



    public String getImagePath() {
        return imagePath.get();
    }

    public SimpleStringProperty imagePathProperty() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath.set(imagePath);
    }



    public String getTagType() {
        return tagType.get();
    }

    public SimpleStringProperty tagTypeProperty() {
        return tagType;
    }

    public void setTagType(String tagType) {
        this.tagType.set(tagType);
    }



    public String getTagValue() {
        return tagValue.get();
    }

    public SimpleStringProperty tagValueProperty() {
        return tagValue;
    }

    public void setTagValue(String tagValue) {
        this.tagValue.set(tagValue);
    }


}
