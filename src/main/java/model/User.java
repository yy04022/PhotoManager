package model;

import javafx.beans.property.SimpleStringProperty;

import java.io.Serializable;

public class User implements Serializable {
    String username;
//    SimpleStringProperty username = new SimpleStringProperty();
    public User(String username) {
        setUsername(username);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    //    public String getUsername() {
//        return username.get();
//    }
//
//    public SimpleStringProperty usernameProperty() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username.set(username);
//    }

    @Override
    public String toString() {
        return getUsername();
    }
}
