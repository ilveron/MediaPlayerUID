package it.unical.sadstudents.mediaplayeruid.model;

import it.unical.sadstudents.mediaplayeruid.controller.MainController;
import javafx.beans.property.*;
import javafx.collections.MapChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

public class Player {
    private static Player instance = null;

    private int skipMilliseconds = 10000;

    private Timer timer;
    private TimerTask task;
    private boolean runningTimer;

    //private File file;
    //private String path;
    private Media media;
    private MediaPlayer mediaPlayer;
    private MediaView mediaView;

    private SimpleStringProperty nameMedia = new SimpleStringProperty();

    private SimpleBooleanProperty isRunning = new SimpleBooleanProperty(false);

    private SimpleDoubleProperty current = new SimpleDoubleProperty(0);
    private SimpleDoubleProperty end = new SimpleDoubleProperty(0);

    private Player() { }

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

    public SimpleDoubleProperty currentProperty() {
        return current;
    }


    public double getEnd() {
        return end.get();
    }

    public SimpleDoubleProperty endProperty() {
        return end;
    }


    public static Player getInstance(){
        if (instance==null)
            instance = new Player();
        return instance;
    }

    public void createMedia(File file){
        String uri = file.toURI().toString();
        media = new Media(uri);
        mediaPlayer = new MediaPlayer(media);
        nameMedia.set(file.getName());
        beginTimer();
        playMedia();
        //TODO: REGEX per riproduzione *.mp4
    }

    public void pauseMedia(){
        if(media != null){
            mediaPlayer.pause();
            isRunning.set(false);
            cancelTimer();
        }
    }

    public void playMedia(){
        if(media != null){
            mediaPlayer.play();
            isRunning.set(true);
            beginTimer();
        }
    }


    public void settaVolume(double v) {

        mediaPlayer.setVolume(v);
    }


    public void beginTimer(){

        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                runningTimer = true;
                current.set(mediaPlayer.getCurrentTime().toSeconds());
                end.set(media.getDuration().toSeconds());


                if (getCurrent()/getEnd() ==1){
                    cancelTimer();
                }
            }
        };
        timer.scheduleAtFixedRate(task,0,1000);

    }

    public void cancelTimer(){
        runningTimer = false;
        timer.cancel();

    }

    public void tenSecondBack() {
        mediaPlayer.seek(Duration.millis((mediaPlayer.getCurrentTime().toMillis()-skipMilliseconds)));

    }


    public void previousMedia() {

    }

    public void tenSecondForward() {
        mediaPlayer.seek(Duration.millis((mediaPlayer.getCurrentTime().toMillis()+skipMilliseconds)));
    }

    @FXML
    void onNext(ActionEvent event) {

    }

    public void restart() {
        if(media != null)
            mediaPlayer.seek(Duration.seconds(0.0));
            mediaPlayer.play();
            beginTimer();
    }





}
