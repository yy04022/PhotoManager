package com.example.photo;

import javafx.beans.property.SimpleStringProperty;

public class Album {

    SimpleStringProperty user = new SimpleStringProperty();
    SimpleStringProperty name = new SimpleStringProperty();


    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
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


}
