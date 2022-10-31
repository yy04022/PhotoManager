package com.example.photo;

import javafx.event.ActionEvent;

public class UserDashboard extends Controller{
    public void createAblumButtonClick(ActionEvent actionEvent) {
    }

    public void deleteAlbumButtonClick(ActionEvent actionEvent) {
    }

    public void editAlbumButtonClick(ActionEvent actionEvent) {
    }

    public void openAlbumButtonClick(ActionEvent actionEvent) {
        //get selected index
        loadPage("AlbumView.fxml","Album View",actionEvent);
    }

    public void onLogoutButtonClick(ActionEvent actionEvent) {
        loadPage("Login.fxml","Login",actionEvent);
    }

    public void photoSearchButtonClick(ActionEvent actionEvent) {

        loadPage("PhotoSearch.fxml","PhotoSearch",actionEvent);

    }
}
