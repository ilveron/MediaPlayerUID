package it.unical.sadstudents.mediaplayeruid.controller;

import it.unical.sadstudents.mediaplayeruid.model.*;
import it.unical.sadstudents.mediaplayeruid.view.RecentMedia;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    private ListView listView;
    @FXML
    private ImageView imageTest;


    // TODO: 06/06/2022 DECIDERE SE SWITCHARE IN PLAYQUEUE xd !!!
    @FXML
    void addFiles(ActionEvent event) {
        //invoca la funzione di PlayQueue per creare una coda da file
        Home.getInstance().addMediaToPlayAndLibrary(RetrievingEngine.getInstance().retrieveFile(0));

    }

    @FXML
    void addFolder(ActionEvent event) {
        //invoca la funzione di PlayQueue per creare una coda da folder
        Home.getInstance().addMediaToPlayAndLibrary(RetrievingEngine.getInstance().retrieveFolder(0));
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        startToolTip();
        for (MyMedia myMedia: Home.getInstance().getRecentMedia()){
            RecentMedia recentMedia = new RecentMedia(myMedia);
            listView.getItems().add(recentMedia);

        }


           // TODO: 05/06/2022 INSERIRE IL CONTAINER CHE SARA' ASSOCIATO ALLA OBSERVABLE LIST DEL MODEL



    }

    private void startToolTip() {
        // TODO: 07/06/2022
    }


}