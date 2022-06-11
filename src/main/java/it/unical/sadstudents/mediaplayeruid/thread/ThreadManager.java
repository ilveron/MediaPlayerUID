package it.unical.sadstudents.mediaplayeruid.thread;

import it.unical.sadstudents.mediaplayeruid.model.*;
import javafx.application.Platform;
import javafx.collections.MapChangeListener;
import javafx.concurrent.Task;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.*;

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

     public void createMediaBis(List<File> files){
         List<MyMedia> provv = new ArrayList<>();
         thread =new Thread(new MediaCreatorThread(files,provv));
         thread.setDaemon(true);
         thread.start();

    }


   public void generateMetadata(List<MyMedia> myMediaList) throws InterruptedException {

        Media media;
        MediaPlayer mediaPlayer;
        for (MyMedia myMedia : myMediaList) {
            media = new Media(myMedia.getPath());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setOnReady(new MetaDataApply(myMedia,mediaPlayer));
            synchronized(obj){
                obj.wait(100);
            }MetaDataApply metaDataApply = new MetaDataApply(myMedia,mediaPlayer);

        }
    }

    public void generateMetadataBis(List<MyMedia> myMediaList) throws InterruptedException {
        long iniziale = currentTimeMillis();
        int cont=0;
        Media media;
        MediaPlayer mediaPlayer;
        for (MyMedia myMedia : myMediaList) {
            media = new Media(myMedia.getPath());
            mediaPlayer = new MediaPlayer(media);
            synchronized(obj){
                obj.wait(20);
            }
            Map<String, Object> metadata = mediaPlayer.getMedia().getMetadata();
            if (metadata != null && metadata.get("title") != null) {
                myMedia.setTitle(metadata.get("title").toString());
                cont++;
            }
            if (metadata != null && metadata.get("artist") != null) {
                myMedia.setArtist(metadata.get("artist").toString());
            }
            if (metadata != null && metadata.get("album") != null) {
                myMedia.setAlbum(metadata.get("album").toString());
            }
            if (metadata != null && metadata.get("genre") != null) {
                myMedia.setGenre(metadata.get("genre").toString());
            }
            if (metadata != null && metadata.get("year") != null) {
                myMedia.setYear((Integer) metadata.get("year"));
            }
            if (metadata != null && metadata.get("length") != null) {
                myMedia.setLength((Double) metadata.get("length"));
            }

            synchronized(ThreadManager.getInstance().getObj()){//this is required since mp.setOnReady creates a new thread and our loopp  in the main thread
                ThreadManager.getInstance().getObj().notify();// the loop has to wait unitl we are able to get the media metadata thats why use .wait() and .notify() to synce the two threads(main thread and MediaPlayer thread)
            }
            mediaPlayer.dispose();

        }
        System.out.println("Tempo esecuzione metadata: "+(currentTimeMillis()-iniziale));
        System.out.println("trovati n metadata: "+cont);
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
