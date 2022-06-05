package it.unical.sadstudents.mediaplayeruid.model;

import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

import java.util.Timer;
import java.util.TimerTask;

public class Player {
    //VARIABLES
    private int skipMilliseconds = 10000;
    private Double volume = 50.0 ;
    private Timer timer;
    private TimerTask task;
    private boolean runningTimer;
    private Media media;
    private MediaPlayer mediaPlayer;
    private MediaView mediaView ;
    //END VARIABLES

    //PROPERTY
    private SimpleDoubleProperty current = new SimpleDoubleProperty(0);
    private SimpleDoubleProperty end = new SimpleDoubleProperty(0);
    private SimpleStringProperty nameMedia = new SimpleStringProperty();
    private SimpleBooleanProperty mediaLoaded = new SimpleBooleanProperty(false);
    private SimpleBooleanProperty isRunning = new SimpleBooleanProperty(false);
    //END PROPERTY

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

    public void setNameMedia(String nameMedia) {
        this.nameMedia.set(nameMedia);
    }
    public String getNameMedia() {
        return nameMedia.get();
    }
    public SimpleStringProperty nameMediaProperty() {
        return nameMedia;
    }

    public SimpleBooleanProperty isRunningProperty() {
        return isRunning;
    }
    public boolean getIsRunning() { return isRunning.get(); }

    public double getCurrent() {
        return current.get();
    }
    public void setCurrent(double current) {
        this.current.set(current);
    }
    public SimpleDoubleProperty currentProperty() {
        return current;
    }

    public double getEnd() {
        return end.get();
    }
    public SimpleDoubleProperty endProperty() {
        return end;
    }

    //END VARIABLES GETTERS AND SETTERS

    //GETTERS AND SETTERS NEEDED FOR SET VIDEO TAB
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
        nameMedia.set(PlayQueue.getInstance().getQueue().get(index).getTitle());
        media = new Media(PlayQueue.getInstance().getQueue().get(index).getPath());
        mediaPlayer = new MediaPlayer(media);
        mediaView.setMediaPlayer(mediaPlayer);
        mediaLoaded.set(true);
        playMedia();
        //TODO: REGEX per riproduzione *.mp4
    }

    public void playMedia(){
        if(media != null){
            mediaPlayer.setVolume(volume);
            mediaPlayer.play();
            isRunning.set(true);
            beginTimer();
        }
    }
    //END FUNCTION START POINT FOR MEDIA REPRODUCTION

    //FUNCTION: BASIC MEDIA CONTROLS
    public void pauseMedia(){
        if(media != null){
            mediaPlayer.pause();
            isRunning.set(false);
            //cancelTimer();
        }
    }

    public void changePosition(double position){
        mediaPlayer.seek(Duration.seconds(position));
        beginTimer();
    }

    public void tenSecondBack() {
        mediaPlayer.seek(Duration.millis((mediaPlayer.getCurrentTime().toMillis()-skipMilliseconds)));

    }

    public void tenSecondForward() {
        mediaPlayer.seek(Duration.millis((mediaPlayer.getCurrentTime().toMillis()+skipMilliseconds)));
    }

    public void stop(){
        pauseMedia();
        setMediaLoaded(false);
        nameMediaProperty().set("");
    }

    public void restart() {
        if(media != null)
            mediaPlayer.seek(Duration.seconds(0.0));
        mediaPlayer.play();
        beginTimer();
    }

    public void setVolume(double v) {
        volume= v;
        mediaPlayer.setVolume(v);
    }
    //END BASIC CONTROLS

    //TASK PROGRESS BAR
    public void beginTimer(){

        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                runningTimer = true;

                current.set(mediaPlayer.getCurrentTime().toSeconds());
                end.set(media.getDuration().toSeconds());

                if (getCurrent()/getEnd()==1){
                    cancelTimer();
                    PlayQueue.getInstance().setCurrentMedia(PlayQueue.getInstance().getCurrentMedia() + 1);
                }
            }
        };
        timer.scheduleAtFixedRate(task,0,1000);

    }

    public void cancelTimer(){
        runningTimer = false;
        timer.cancel();
    }
    //END TASK



}
