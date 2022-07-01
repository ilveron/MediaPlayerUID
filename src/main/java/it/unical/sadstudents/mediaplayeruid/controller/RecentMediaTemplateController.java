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
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import java.io.IOException;

public class RecentMediaTemplateController {

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




    public void init(MyMedia myMedia){
        artistLabel.setText(myMedia.getArtist());
        nameLabel.setText(myMedia.getTitle());
        imageView.setImage(new Image("file:"+"src/main/resources/it/unical/sadstudents/mediaplayeruid/image/iconaMusica.png"));


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

            //MediaView mv = new MediaView();
            mediaViewBis.setMediaPlayer(mediaPlayer);

            mediaPlayer.setOnReady(()->{
                int width = (int)mediaViewBis.getFitWidth();
                int height = (int)mediaViewBis.getFitHeight();
                System.out.println(width);
                System.out.println(height);
                WritableImage wim = new WritableImage(width, height);
                System.out.println(mediaPlayer.getStatus());
                mediaPlayer.seek(Duration.seconds(20));
                mediaPlayer.play();
                mediaViewBis.snapshot(null, wim);
                //BufferedImage image = wim.getPixelWriter();

                mediaPlayer.stop();
                mediaPlayer.dispose();
                mediaViewBis.setVisible(false);

                imageView.setImage(wim);
            });


        }*/


        imageView.setFitHeight(200);
        imageView.setFitWidth(200);


    }

    public void onMouseOver(){
        if(!onActionPane.isVisible()){
            onActionPane.setVisible(true);
            actionAnchorPane.setVisible(true);

        }
        else{
            onActionPane.setVisible(false);
            actionAnchorPane.setVisible(false);

        }
    }


}
