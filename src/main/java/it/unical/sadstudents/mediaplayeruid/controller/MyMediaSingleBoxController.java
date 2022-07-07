package it.unical.sadstudents.mediaplayeruid.controller;

import it.unical.sadstudents.mediaplayeruid.model.*;
import it.unical.sadstudents.mediaplayeruid.view.MediaInfo;
import it.unical.sadstudents.mediaplayeruid.view.SceneHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.MediaView;

public class MyMediaSingleBoxController {
    @FXML
    private Label labelDuration;
    @FXML
    private StackPane stackPaneRecent;

    @FXML
    private AnchorPane actionAnchorPane;

    @FXML
    private Label artistLabel;
    @FXML
    private MediaView   mediaViewBis;

    @FXML
    private ImageView imageView;

    @FXML
    private Label nameLabel;
    @FXML
    private AnchorPane mainRoot;
    @FXML
    private Pane onActionPane;

    @FXML
    void onDeleteAction(ActionEvent event) {
        if(SceneHandler.getInstance().showConfirmationAlert("Do you really want to delete the file?")) {
            if (source == "home") {
                for (int i = 0; i < Home.getInstance().getRecentMedia().size(); i++) {
                    if (myMedia.equals(Home.getInstance().getRecentMedia().get(i))) {
                        Home.getInstance().removeItem(i);
                        break;
                    }
                }
            } else {
                for (int i = 0; i < VideoLibrary.getInstance().getVideoLibrary().size(); i++) {
                    if (myMedia.equals(VideoLibrary.getInstance().getVideoLibrary().get(i))) {

                        for (int j=0; j<Home.getInstance().getRecentMedia().size();j++){
                            if (myMedia.equals(Home.getInstance().getRecentMedia().get(j))){
                                Home.getInstance().removeItem(j);
                            }
                        }
                        VideoLibrary.getInstance().removeWithIndex(i);
                        break;
                    }
                }
            }
        }
    }

    @FXML
    void onPlayAction(ActionEvent event) {
        PlayQueue.getInstance().generateNewQueue(myMedia);
    }



    private MyMedia myMedia;
    private String source;
    public void init(MyMedia myMedia, String source){
        this.source=source;
        this.myMedia=myMedia;
        artistLabel.setText(myMedia.getArtist());
        nameLabel.setText(myMedia.getTitle());
        imageView.setImage(new Image("file:"+"src/main/resources/it/unical/sadstudents/mediaplayeruid/image/iconaMusica.png"));
        labelDuration.setText(myMedia.getLength());


    }

    public void setImage(Image image){
        imageView.setImage(image);
    }

    public Image getImage(){
        return imageView.getImage();
    }

    public MediaView getMediaViewBis(){
        return mediaViewBis;
    }

    public void onMouseOver(){
        if(!actionAnchorPane.isVisible()){
            actionAnchorPane.setVisible(true);
            imageView.setEffect(new GaussianBlur(20));

        }
        else{
            actionAnchorPane.setVisible(false);
            imageView.setEffect(null);
        }

    }

}
