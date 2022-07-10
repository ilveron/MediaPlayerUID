package it.unical.sadstudents.mediaplayeruid.view;

import it.unical.sadstudents.mediaplayeruid.MainApplication;
import it.unical.sadstudents.mediaplayeruid.controller.MediaInfoController;
import it.unical.sadstudents.mediaplayeruid.model.MyMedia;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class MediaInfo extends Pane {

    MediaInfoController mediaInfoController;
    private boolean showed=false;

    public MediaInfo(MyMedia myMedia) {
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("mediaInfo-view.fxml"));
        Pane root = null;
        try {
            root = loader.load();
            mediaInfoController = loader.getController();
            mediaInfoController.init(myMedia);
            this.getChildren().add(root);
            //root.prefWidthProperty().bind(this.widthProperty());
        } catch (Exception ignoredException) {
            ignoredException.printStackTrace();
        }

    }

    public boolean isShowed() {
        return showed;
    }

    public void setShowed(boolean showed) {
        this.showed = showed;
    }

    public void setImage(Image image){
        mediaInfoController.setImage(image);
    }

}


