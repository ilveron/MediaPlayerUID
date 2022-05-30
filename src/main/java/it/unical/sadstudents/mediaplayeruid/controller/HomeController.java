package it.unical.sadstudents.mediaplayeruid.controller;

import it.unical.sadstudents.mediaplayeruid.model.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;

public class HomeController {
    @FXML
    void addFiles(ActionEvent event) {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose the file");
        File file = fileChooser.showOpenDialog(stage);
        Player.getInstance().playMedia(file);


    }

}