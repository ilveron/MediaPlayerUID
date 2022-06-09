package it.unical.sadstudents.mediaplayeruid.controller;

import it.unical.sadstudents.mediaplayeruid.model.MyMedia;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class RecentMediaTemplateController {

    @FXML
    private Label artistLabel;

    @FXML
    private ImageView imageView;

    @FXML
    private Label nameLabel;
    @FXML
    private AnchorPane mainRoot;


    public void init(MyMedia myMedia){
        artistLabel.setText(myMedia.getArtist());
        nameLabel.setText(myMedia.getTitle());
        imageView.setImage(myMedia.getImage());
        imageView.setFitHeight(200);
        imageView.setFitWidth(200);
    }
}
