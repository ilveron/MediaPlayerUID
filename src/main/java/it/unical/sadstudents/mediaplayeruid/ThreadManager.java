package it.unical.sadstudents.mediaplayeruid;

import it.unical.sadstudents.mediaplayeruid.model.Player;
import javafx.application.Platform;
import javafx.collections.MapChangeListener;
import javafx.scene.media.Media;

import java.util.Timer;
import java.util.TimerTask;

public class ThreadManager {
    private Timer timer;
    private TimerTask timerTask;

    //SINGLETON
    private static ThreadManager instance = null;

    public static ThreadManager getInstance(){
        if(instance==null)
            instance = new ThreadManager();
        return instance;
    }

    private ThreadManager() {}
    //END SINGLETON

    public void metaDataGenerator(){
        // TODO: 05/06/2022
    }

    public void mediaPlayingNowDataDisplayedUpdate(){
        // TODO: 05/06/2022
    }

    public void progressBarUpdate(){
        // TODO: 05/06/2022
    }
    
    public void listGenerator(){
        // TODO: 05/06/2022
    }

    //TASK PROGRESS BAR
    public void beginTimer(){
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        Player.getInstance().setCurrentMediaTime(Player.getInstance().getMediaPlayer().getCurrentTime().toSeconds());
                        Player.getInstance().setEndMediaTime(Player.getInstance().getMediaPlayer().getTotalDuration().toSeconds());
                    }
                });
            }
        };
        timer.scheduleAtFixedRate(timerTask,0,1000);

    }

    public void cancelTimer(){
        timer.cancel();
    }
    //END TASK

    public void setNameAndArtistLabels(Media media){
        media.getMetadata().addListener((MapChangeListener<String, Object>) change -> {
            if(change.wasAdded()) {
                //System.out.println(media.getMetadata());
                if ("title".equals(change.getKey())) {
                    if (media.getMetadata().get("title").toString() != null)
                        Player.getInstance().setMediaName(media.getMetadata().get("title").toString());
                }
                else if ("artist".equals(change.getKey())) {
                    Player.getInstance().setArtistName(media.getMetadata().get("artist").toString());
                }
            }
        });
    }

}
