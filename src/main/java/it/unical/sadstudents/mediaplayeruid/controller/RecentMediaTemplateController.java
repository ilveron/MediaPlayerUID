package it.unical.sadstudents.mediaplayeruid.controller;

import it.unical.sadstudents.mediaplayeruid.model.MyMedia;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class RecentMediaTemplateController {

    @FXML
    private Label artistLabel;

    @FXML
    private Label nameLabel;


    public void init(MyMedia myMedia){
        artistLabel.setText(myMedia.getArtist());
        nameLabel.setText(myMedia.getTitle());
    }
}
