package it.unical.sadstudents.mediaplayeruid.controller;

import it.unical.sadstudents.mediaplayeruid.model.PlayQueue;
import it.unical.sadstudents.mediaplayeruid.model.RetrievingEngine;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {



    @FXML
    void addFile(ActionEvent event) {
        //invoca la funzione di PlayQueue per creare una coda da file
        PlayQueue.getInstance().generateNewQueue(RetrievingEngine.getInstance().retrieveFile()) ;
    }

    @FXML
    void addFolder(ActionEvent event) {
        //invoca la funzione di PlayQueue per creare una coda da folder
        PlayQueue.getInstance().generateNewQueueFromList(RetrievingEngine.getInstance().retrieveFolder());
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}