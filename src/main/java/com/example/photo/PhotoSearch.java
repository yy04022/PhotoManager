package com.example.photo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PhotoSearch {
    public TextField tagTypeTF;
    public TextField tagValueTF;

    public void searchButtonClick(ActionEvent actionEvent) {
    }

    public void datePickerSearchButtonClick(ActionEvent actionEvent) {
    }

    public void createFromResultButtonClick(ActionEvent actionEvent) {
        Album album = new Album(null, null, null, null, null);
        //add param to this ^
        Date d = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String sd = dateFormat.format(d);
        album.setDate(sd);
    }
}
