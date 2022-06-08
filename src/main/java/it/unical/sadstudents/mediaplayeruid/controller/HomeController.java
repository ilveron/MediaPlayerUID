package it.unical.sadstudents.mediaplayeruid.controller;

import it.unical.sadstudents.mediaplayeruid.model.*;
import it.unical.sadstudents.mediaplayeruid.view.RecentMedia;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    private ImageView imageTest;


    // TODO: 06/06/2022 DECIDERE SE SWITCHARE IN PLAYQUEUE xd !!!
    @FXML
    void addFile(ActionEvent event) {
        //invoca la funzione di PlayQueue per creare una coda da file
        //PlayQueue.getInstance().generateNewQueue(RetrievingEngine.getInstance().retrieveFile(0)) ;
        File file = RetrievingEngine.getInstance().retrieveFile(0);
        MyMedia myMedia = new MyMedia(file);
        MyMediaDatabase.getInstance().addToDatabase(myMedia);
        Integer key = myMedia.hashCode();
        Player.getInstance().createMediaTest(MyMediaDatabase.getInstance().getMyDataBase().get(key));
    }

    @FXML
    void addFolder(ActionEvent event) {
        //invoca la funzione di PlayQueue per creare una coda da folder
        PlayQueue.getInstance().generateNewQueueFromList(RetrievingEngine.getInstance().retrieveFolder(0));
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        startToolTip();
        ArrayList<RecentMedia> recentMediaList = new ArrayList<RecentMedia>();
        for (MyMedia myMedia: Home.getInstance().getRecentMedia()){
            RecentMedia recentMedia = new RecentMedia();
            //recentMedia.getPane().getChildren().getClass().getC
            recentMediaList.add(new RecentMedia());

        }


           // TODO: 05/06/2022 INSERIRE IL CONTAINER CHE SARA' ASSOCIATO ALLA OBSERVABLE LIST DEL MODEL



    }

    private void startToolTip() {
        // TODO: 07/06/2022
    }


}