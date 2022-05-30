package it.unical.sadstudents.mediaplayeruid.model;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.io.File;

public class Player {
    private static Player instance = null;

    //private File file;
    //private String path;
    private Media media;
    private MediaPlayer mediaPlayer;
    private MediaView mediaView;
    private boolean isRunning = false;

    private Player() { }

    public boolean getIsRunning() { return isRunning; }

    public static Player getInstance(){
        if (instance==null)
            instance = new Player();
        return instance;
    }

    public void playMedia(File file){
        String uri = file.toURI().toString();
        media = new Media(uri);
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
        isRunning = true;
        //TODO: REGEX per riproduzione *.mp4
    }

    public void pauseMedia(){
        if(media != null){
            mediaPlayer.pause();
            isRunning = false;
        }
    }

    public void resumeMedia(){
        if(media != null){
            mediaPlayer.play();
            isRunning = true;
        }
    }


    public void settaVolume(double v) {
        mediaPlayer.setVolume(v);
    }


}
