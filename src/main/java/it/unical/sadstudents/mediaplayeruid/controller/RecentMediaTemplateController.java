package it.unical.sadstudents.mediaplayeruid.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class RecentMediaTemplateController {

    @FXML
    private Label artistLabel;

    @FXML
    private Label nameLabel;


    public void setData(String artist, String name){
        artistLabel.setText(artist);
        nameLabel.setText(name);
    }
}
