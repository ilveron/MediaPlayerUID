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
        /*try{
            if (!myMedia.getPath().toLowerCase().endsWith(".mp4")) {
                Media media = new Media(myMedia.getPath());
                media.getMetadata().addListener((MapChangeListener<String, Object>) change -> {
                    if (change.wasAdded()) {
                        if (media.getMetadata().get("image") != null) {
                            Object newImage = (media.getMetadata().get("image"));
                            Image image = ((Image) newImage);
                            imageView.setImage(image);
                        }
                    }
                });
            }
        }
        catch(Exception exception){}*/
    }

    public void setImage(Image image) {
        imageView.setImage(image);
    }

    /*public Image getImage (MyMedia myMedia) {


         else {
            Media media = new Media(myMedia.getPath());
            MediaPlayer mediaPlayer = new MediaPlayer(media);

            //MediaView mv = new MediaView();
            mediaViewBis.setMediaPlayer(mediaPlayer);

            mediaPlayer.setOnReady(() -> {
                int width = (int) mediaViewBis.getFitWidth();
                int height = (int) mediaViewBis.getFitHeight();
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
                            //imageView.setImage(snapshotResult.getImage());
                            //saveToFile(snapshotResult.getImage());
                            mediaViewBis.setVisible(false);
                            mediaPlayer.dispose();
                            return null;
                        }
                    }, null, wim);
                    imageView.setImage(wim);
                    //imageView.setFitHeight(200);
                    //imageView.setFitWidth(200);
                });
                service.start();
                //BufferedImage image = wim.getPixelWriter();
                //System.out.println(wimBis);

                //mediaPlayer.stop();
                //mediaViewBis.setVisible(false);
                //               imageView.setImage(wim);
            });


        }
    }*/


}
