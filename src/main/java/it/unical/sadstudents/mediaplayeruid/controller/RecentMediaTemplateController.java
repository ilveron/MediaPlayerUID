package it.unical.sadstudents.mediaplayeruid.controller;

import it.unical.sadstudents.mediaplayeruid.model.MyMedia;
import it.unical.sadstudents.mediaplayeruid.model.PlayQueue;
import javafx.collections.MapChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

import javax.imageio.ImageIO;
import java.io.IOException;

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
        //imageView.setImage(new Image("file:"+"src/main/resources/it/unical/sadstudents/mediaplayeruid/image/download.png"));
        imageView.setImage(new Image("file:"+myMedia.getImageUrl()));

        if (!myMedia.getPath().toLowerCase().endsWith(".mp4")){
            Media media = new Media(myMedia.getPath());
            media.getMetadata().addListener((MapChangeListener<String, Object>) change -> {
                if(change.wasAdded()) {
                    if (media.getMetadata().get("image") != null){
                        Object newImage = (media.getMetadata().get("image"));
                        Image image = ((Image)newImage);
                        imageView.setImage(image);
                    }
                }
            });
        }
        /*else{
            Media media = new Media(myMedia.getPath());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            MediaView mediaView = new MediaView(mediaPlayer);
            int width = mediaPlayer.getMedia().getWidth();
            int height = mediaPlayer.getMedia().getHeight();
            WritableImage wim = new WritableImage(width, height);

            mediaView.setFitWidth(width);
            mediaView.setFitHeight(height);
            //mv.setMediaPlayer(mediaPlayer);
            mediaPlayer.seek(Duration.seconds ((media.getDuration().toSeconds()/100.00)*10.00));
            mediaView.snapshot(null, wim);
            Image image = (Image)wim;
            imageView.setImage(image);
        }*/


        imageView.setFitHeight(200);
        imageView.setFitWidth(200);
    }
}
