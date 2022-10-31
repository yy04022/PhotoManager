package com.example.photo;

import javafx.beans.property.SimpleStringProperty;

public class User {
    SimpleStringProperty username = new SimpleStringProperty();
    public User(String username) {
        setUsername(username);
    }


    public String getUsername() {
        return username.get();
    }

    public SimpleStringProperty usernameProperty() {
        return username;
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    @Override
    public String toString() {
        return getUsername();
    }
}
