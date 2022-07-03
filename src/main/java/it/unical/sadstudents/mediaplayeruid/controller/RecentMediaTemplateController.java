package it.unical.sadstudents.mediaplayeruid.controller;

import it.unical.sadstudents.mediaplayeruid.model.Home;
import it.unical.sadstudents.mediaplayeruid.model.MyMedia;
import it.unical.sadstudents.mediaplayeruid.model.PlayQueue;
import javafx.collections.MapChangeListener;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.SnapshotResult;
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
import javafx.util.Callback;
import javafx.util.Duration;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class RecentMediaTemplateController {
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
        for (int i=0; i<Home.getInstance().getRecentMedia().size();i++){
            if(myMedia.equals(Home.getInstance().getRecentMedia().get(i))){
                Home.getInstance().getRecentMedia().remove(i);
                Home.getInstance().setChangeHappened(true);
                break;
            }
        }
    }

    @FXML
    void onPlayAction(ActionEvent event) {
        PlayQueue.getInstance().generateNewQueue(myMedia);
    }



    private MyMedia myMedia;
    public void init(MyMedia myMedia){
        this.myMedia=myMedia;
        artistLabel.setText(myMedia.getArtist());
        nameLabel.setText(myMedia.getTitle());
        imageView.setImage(new Image("file:"+"src/main/resources/it/unical/sadstudents/mediaplayeruid/image/iconaMusica.png"));
        labelDuration.setText(myMedia.getLength());


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
        else{
            Media media = new Media(myMedia.getPath());
            MediaPlayer mediaPlayer = new MediaPlayer(media);

            //MediaView mv = new MediaView();
            mediaViewBis.setMediaPlayer(mediaPlayer);

            mediaPlayer.setOnReady(()->{
                int width = (int)mediaViewBis.getFitWidth();
                int height = (int)mediaViewBis.getFitHeight();
                //System.out.println(width);
                //System.out.println(height);
                WritableImage wim = new WritableImage(width, height);
                //System.out.println(mediaPlayer.getStatus());
                // mediaPlayer.seek(Duration.seconds(15));
                mediaViewBis.setVisible(true);
                mediaPlayer.seek(Duration.seconds(5));
                mediaPlayer.play();
                mediaPlayer.pause();
                Service<Void> service = new Service<>() {
                    @Override
                    protected Task<Void> createTask() {
                        return new Task<>() {
                            @Override
                            protected Void call() throws Exception {
                                Thread.sleep(5000);
                                return null;
                            }
                        };
                    }
                };
                service.setOnSucceeded(event -> {
                    mediaViewBis.snapshot(new Callback<SnapshotResult, Void>() {
                        @Override
                        public Void call(SnapshotResult snapshotResult) {
                            //System.out.println("QUI");
                            imageView.setImage(snapshotResult.getImage());
                            //saveToFile(snapshotResult.getImage());
                            mediaViewBis.setVisible(false);
                            mediaPlayer.dispose();
                            return null;
                        }
                    }, null, wim);
                    //imageView.setImage(wim);
                });
                service.start();
                //BufferedImage image = wim.getPixelWriter();
                //System.out.println(wimBis);

                //mediaPlayer.stop();
                //mediaViewBis.setVisible(false);
                //               imageView.setImage(wim);
            });


        }


        imageView.setFitHeight(200);
        imageView.setFitWidth(200);


    }

    public void onMouseOver(){
        if(!actionAnchorPane.isVisible()){
            //onActionPane.setVisible(true);
            actionAnchorPane.setVisible(true);

        }
        else{
            //onActionPane.setVisible(false);
            actionAnchorPane.setVisible(false);

        }

    }

    /*public void saveToFile(Image image) {
        File outputFile = new File("/Users/carmine/Desktop/prova.png");
        BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
        try {
            ImageIO.write(bImage, "png", outputFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }*/


}
