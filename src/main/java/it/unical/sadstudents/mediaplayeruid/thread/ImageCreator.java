package it.unical.sadstudents.mediaplayeruid.thread;

import it.unical.sadstudents.mediaplayeruid.view.MyMediaSingleBox;
import javafx.collections.MapChangeListener;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.SnapshotResult;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Callback;
import javafx.util.Duration;


public class ImageCreator extends Service<Boolean> {
    MyMediaSingleBox myMediaSingleBox;

    public void setPane(MyMediaSingleBox myMediaSingleBox) {
        this.myMediaSingleBox = myMediaSingleBox;
    }

    @Override
    protected Task<Boolean> createTask() {
        return new Task<Boolean>(){
        @Override
        protected Boolean call() throws Exception{
            if (!myMediaSingleBox.getMyMedia().getPath().toLowerCase().endsWith(".mp4")){
                Media media = new Media(myMediaSingleBox.getMyMedia().getPath());
                media.getMetadata().addListener((MapChangeListener<String, Object>) change -> {
                    if(change.wasAdded()) {
                        if (media.getMetadata().get("image") != null){
                            Object newImage = (media.getMetadata().get("image"));
                            Image image = ((Image)newImage);
                            myMediaSingleBox.setImage(image);
                        }
                    }
                });
            }
            else{
                Media media = new Media(myMediaSingleBox.getMyMedia().getPath());
                MediaPlayer mediaPlayer = new MediaPlayer(media);

                MediaView mediaViewBis = myMediaSingleBox.getMediaView();
                mediaViewBis.setMediaPlayer(mediaPlayer);

                mediaPlayer.setOnReady(()->{

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
                                    Thread.sleep(2000);
                                    return null;
                                }
                            };
                        }
                    };
                    service.setOnSucceeded(event -> {
                        double mediaWidth = mediaPlayer.getMedia().getWidth();
                        double mediaHeight = mediaPlayer.getMedia().getHeight();
                        double ratio = 200/mediaWidth;

                        int width = 200;
                        int height = (int) (mediaHeight*ratio);




                        WritableImage wim = new WritableImage(width, height);
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
                        myMediaSingleBox.setImage(wim);

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
            return true;
        }

        };

    }
}
