package it.unical.sadstudents.mediaplayeruid.thread;

import it.unical.sadstudents.mediaplayeruid.model.*;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.System.currentTimeMillis;

public class ThreadManager {
    private Timer timer;
    private TimerTask timerTask;
    private Task task;
    private Thread thread;
    private final Object obj= new Object();

    public Thread getThread() {
        return thread;
    }

    public Object getObj() {
        return obj;
    }

    //SINGLETON
    private static ThreadManager instance = null;
    public static ThreadManager getInstance(){
        if(instance==null)
            instance = new ThreadManager();
        return instance;
    }
    private ThreadManager() {    }
    //END SINGLETON



    public MyMedia createMyMedia(File file){
        MyMedia myMedia =new MyMedia(file);

        /*MediaCreatorThread metaDataGenerator = new MediaCreatorThread(myMedia);
        Thread  thread = new Thread(metaDataGenerator);
        thread.setDaemon(true);
        thread.start();*/



        return myMedia;
    }

     public void createMediaBis(List<File> files, boolean startIsNeeded,boolean resetPlayQueueNeeded) throws InterruptedException {
         List<MyMedia> provv = new ArrayList<>();
         //Platform.runLater(new MediaCreatorThread(files,provv,startIsNeeded,resetPlayQueueNeeded));


         thread =new Thread(new MediaCreatorThread(files,provv,startIsNeeded,resetPlayQueueNeeded));
         thread.setDaemon(true);
         thread.start();
         /*synchronized (obj){
             obj.wait(2000);
         }
         Thread thread1 = new Thread(new MetaDataApply(provv));
         thread1.setDaemon(true);
         thread1.start();*/

    }

    public void startMetadata(List<MyMedia> myMediaList){
        Thread thread1 = new Thread(new MetaDataApply(myMediaList));
        thread1.setDaemon(true);
        thread1.start();
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

    /*public void metadataInRunTime(Media media){
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

    }*/
}
