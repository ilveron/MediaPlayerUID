package it.unical.sadstudents.mediaplayeruid.model;

import it.unical.sadstudents.mediaplayeruid.thread.ThreadManager;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.MapChangeListener;
import javafx.scene.media.EqualizerBand;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

import java.util.Timer;
import java.util.TimerTask;

public class Player {
    //VARIABLES
    private int skipMilliseconds = 10000;
    private Double volume = 100.0 ;
    private Timer timer;
    private TimerTask task;
    private boolean runningTimer;
    private Media media;
    private MediaPlayer mediaPlayer;
    private MediaView mediaView ;
    private Integer index;
    private boolean loopMode;
    //END VARIABLES

    //PROPERTIES
    private SimpleDoubleProperty currentMediaTime = new SimpleDoubleProperty(0);
    private SimpleDoubleProperty endMediaTime = new SimpleDoubleProperty(0);
    private SimpleStringProperty artistName = new SimpleStringProperty();
    private SimpleStringProperty mediaName = new SimpleStringProperty();
    private SimpleBooleanProperty mediaLoaded = new SimpleBooleanProperty(false);
    private SimpleBooleanProperty isRunning = new SimpleBooleanProperty(false);
    //END PROPERTIES

    //SINGLETON
    private static Player instance = null;
    private Player() { }
    public static Player getInstance(){
        if (instance==null)
            instance = new Player();
        return instance;
    }
    //END SINGLETON


    //VARIABLES GETTERS AND SETTERS



    public boolean isMediaLoaded() {
        return mediaLoaded.get();
    }
    public void setMediaLoaded(boolean mediaLoaded) { this.mediaLoaded.set(mediaLoaded);}
    public SimpleBooleanProperty mediaLoadedProperty() {
        return mediaLoaded;
    }

    public void setMediaName(String mediaName) {
        this.mediaName.set(mediaName);
    }
    public String getMediaName() {
        return mediaName.get();
    }
    public SimpleStringProperty mediaNameProperty() {
        return mediaName;
    }

    public SimpleBooleanProperty isRunningProperty() {
        return isRunning;
    }
    public boolean getIsRunning() { return isRunning.get(); }

    public double getCurrentMediaTime() { return currentMediaTime.get(); }
    public void setCurrentMediaTime(double currentMediaTime) { this.currentMediaTime.set(currentMediaTime); }
    public SimpleDoubleProperty currentMediaTimeProperty() { return currentMediaTime; }

    public void setEndMediaTime(double endMediaTime) { this.endMediaTime.set(endMediaTime); }
    public double getEndMediaTime() { return endMediaTime.get(); }
    public SimpleDoubleProperty endMediaTimeProperty() { return endMediaTime; }

    public String getArtistName() { return artistName.get(); }
    public SimpleStringProperty artistNameProperty() { return artistName; }
    public void setArtistName(String artistName) { this.artistName.set(artistName); }

    public boolean isLoopMode() { return loopMode; }
    public void setLoopMode(boolean loopMode) { this.loopMode = loopMode; }

    //END VARIABLES GETTERS AND SETTERS

    //GETTERS AND SETTERS NEEDED TO SET VIDEO TAB
    public MediaView getMediaView() {
        return mediaView;
    }
    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }
    public void setMediaView(MediaView mediaView) {
        this.mediaView = mediaView;
    }
    //END GETTERS AND SETTERS


    //FUNCTIONS: START POINT FOR MEDIA REPRODUCTION
    public void createMedia(Integer index){
        this.index = index;



        media = new Media(PlayQueue.getInstance().getQueue().get(index).getPath());
        mediaPlayer = new MediaPlayer(media);
        mediaView.setMediaPlayer(mediaPlayer);
        mediaLoaded.set(true);
        mediaPlayer.setOnReady(()->{
            // TODO: 14/06/2022 se rimosso, problemi con lo slider 

            playMedia();
        });
        Platform.runLater(()->{
            mediaName.set(PlayQueue.getInstance().getQueue().get(index).getTitle());
            artistName.set(PlayQueue.getInstance().getQueue().get(index).getArtist());
        });


        //TODO: REGEX per riproduzione *.mp4
    }

    //public void createMedia(Integer index){
    /*public void createMediaTest(MyMedia myMedia){

        mediaName.set(myMedia.getTitle());
        media = new Media(myMedia.getPath());
        mediaPlayer = new MediaPlayer(media);
        mediaView.setMediaPlayer(mediaPlayer);
        mediaLoaded.set(true);
        playMedia();
        ThreadManager.getInstance().setNameAndArtistLabels(media);
        //TODO: REGEX per riproduzione *.mp4
    }*/


    public void playMedia(){
        if(media != null){
            mediaPlayer.setVolume(volume);
            mediaPlayer.play();
            isRunning.set(true);
            ThreadManager.getInstance().beginTimer();
            Home.getInstance().addToRecentMedia(PlayQueue.getInstance().getQueue().get(index));
        }
    }
    //END FUNCTION START POINT FOR MEDIA REPRODUCTION

    //FUNCTION: BASIC MEDIA CONTROLS
    public void pauseMedia(){
        if(media != null){
            mediaPlayer.pause();
            isRunning.set(false);
            ThreadManager.getInstance().cancelTimer();
        }
    }

    public void changePosition(double position){
        mediaPlayer.seek(Duration.seconds(position));
    }

    public void tenSecondBack() {
        mediaPlayer.seek(Duration.millis((mediaPlayer.getCurrentTime().toMillis()-skipMilliseconds)));

    }

    public void tenSecondForward() {
        mediaPlayer.seek(Duration.millis((mediaPlayer.getCurrentTime().toMillis()+skipMilliseconds)));
    }

    public void stop(){
        //mediaPlayer.stop();
        mediaPlayer.dispose();
        mediaLoaded.set(false);
        mediaName.set("");
        artistName.set("");
        currentMediaTime.set(0);
        endMediaTime.set(0);
        ThreadManager.getInstance().cancelTimer();
    }

    public void repeat() {
        if(!loopMode)
            loopMode = true;
        else
            loopMode = false;
    }

    public void setVolume(double v) {
        volume=v;
        mediaPlayer.setVolume(v);
    }
    //END BASIC CONTROLS

    public void metadataUpdate(int index){
        mediaPlayer.getMedia().getMetadata().addListener((MapChangeListener<String, Object>) change -> {
            if(change.wasAdded()) {
                //System.out.println(media.getMetadata());
                if ("title".equals(change.getKey())) {
                    if (mediaPlayer.getMedia().getMetadata().get("title").toString() != null){
                        PlayQueue.getInstance().getQueue().get(index).setTitle(mediaPlayer.getMedia().getMetadata().get("title").toString());
                        setMediaName(media.getMetadata().get("title").toString());

                    }
                }
                else if ("artist".equals(change.getKey())) {
                    PlayQueue.getInstance().getQueue().get(index).setArtist(mediaPlayer.getMedia().getMetadata().get("artist").toString());
                    setArtistName(media.getMetadata().get("artist").toString());

                }
                else if ("album".equals(change.getKey())) {
                    PlayQueue.getInstance().getQueue().get(index).setAlbum(mediaPlayer.getMedia().getMetadata().get("album").toString());
                }
                else if ("genre".equals(change.getKey())) {
                    PlayQueue.getInstance().getQueue().get(index).setGenre(mediaPlayer.getMedia().getMetadata().get("genre").toString());
                }
                else if ("year".equals(change.getKey())) {
                    PlayQueue.getInstance().getQueue().get(index).setYear(Integer.parseInt(mediaPlayer.getMedia().getMetadata().get("year").toString()));
                }
                else if (media.getMetadata().get("length") != null){
                    PlayQueue.getInstance().getQueue().get(index).setLength(Double.parseDouble(mediaPlayer.getMedia().getMetadata().get("length").toString()));
                }

            }
        });

    }
}
