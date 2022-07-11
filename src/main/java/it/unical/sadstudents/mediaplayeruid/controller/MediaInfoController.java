package it.unical.sadstudents.mediaplayeruid.controller;

import it.unical.sadstudents.mediaplayeruid.model.MyMedia;
import javafx.collections.MapChangeListener;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.SnapshotResult;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Callback;
import javafx.util.Duration;

public class MediaInfoController  {
    @FXML
    private Label albumLabel,artistLabel,durationLabel,genreLabel,titleLabel,yearLabel;
     @FXML
     private ImageView imageView;

    public void init (MyMedia myMedia){
        titleLabel.setText(myMedia.getTitle());
        artistLabel.setText(myMedia.getArtist());
        albumLabel.setText(myMedia.getAlbum());
        genreLabel.setText(myMedia.getGenre());
        yearLabel.setText(myMedia.getYear());
        durationLabel.setText(myMedia.getLength());
        imageView.setImage(new Image("file:" + "src/main/resources/it/unical/sadstudents/mediaplayeruid/image/iconaMusica.png"));
    }

    public void setImage(Image image) {
        imageView.setImage(image);
    }


}
