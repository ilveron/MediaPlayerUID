package it.unical.sadstudents.mediaplayeruid.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class PlayQueueController implements Initializable {

    @FXML
    private ListView<File> listQueue;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
