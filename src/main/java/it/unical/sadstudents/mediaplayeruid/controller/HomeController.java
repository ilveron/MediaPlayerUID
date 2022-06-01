package it.unical.sadstudents.mediaplayeruid.controller;

import it.unical.sadstudents.mediaplayeruid.model.PlayQueue;
import it.unical.sadstudents.mediaplayeruid.view.MiddlePaneHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.FilenameFilter;

public class HomeController {




    @FXML
    void addFile(ActionEvent event) {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose the file to add");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Music Files","*.mp3", "*.wav");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(stage);

        PlayQueue.getInstance().generateNewQueue(file);


    }

    @FXML
    void addFolder(ActionEvent event) {
        Stage stage = new Stage();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Choose the directory to add");

        File directory = new File(String.valueOf(directoryChooser.showDialog(stage)));
        File[] files = directory.listFiles((new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".mp3") || name.toLowerCase().endsWith("wav");
            }
        }));

        PlayQueue.getInstance().generateNewQueueFromList(files);

    }
    




}