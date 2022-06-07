package it.unical.sadstudents.mediaplayeruid;

import it.unical.sadstudents.mediaplayeruid.model.MyMedia;
import it.unical.sadstudents.mediaplayeruid.model.Player;
import javafx.application.Platform;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.media.Media;

import java.io.File;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class ThreadManager {
    private Timer timer;
    private TimerTask timerTask;
    private Task task;

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

    public void createMedia(ObservableList<MyMedia> mylist, ArrayList<File> newFiles){
        task = new Task<Void>() {
            @Override public Void call() {
                for (int i=0; i<newFiles.size()-1;i++) {
                    mylist.add(new MyMedia(newFiles.get(i)));
                }
                task.cancel();
                return null;
            }
        };


        new Thread(task).start();

    }



    /*public MyMedia createMedia(File file){
        final MyMedia[] myMedia = {null};
        Task task2 = new Task() {
            @Override
            public MyMedia call() throws Exception {
                myMedia[0] = new MyMedia(file);

                return myMedia[0];
            }
        };

        Thread thread = new Thread(task2);
        thread.setDaemon(true);
        thread.start();


        return myMedia[0];
    }
*/





    //TODO: NON CANCELLARE!!!
    /*public void compilaMetadati(){
        for(MyMedia myMedia: queue){
            Media media = new Media(myMedia.getPath());
            media.getMetadata().addListener((MapChangeListener<String, Object>) change -> {
                System.out.println("ciao");
                if(change.wasAdded()){
                    if("title".equals(change.getKey())) {
                        myMedia.setTitle(media.getMetadata().get("title").toString());
                    }
                    else if ("artist".equals(change.getKey())){
                        myMedia.setArtist(media.getMetadata().get("artist").toString());
                    }
                    else if ("album".equals(change.getKey())){
                        myMedia.setAlbum(media.getMetadata().get("album").toString());
                    }
                    else if ("genre".equals(change.getKey())){
                        myMedia.setGenre(media.getMetadata().get("genre").toString());
                    }
                    else if (media.getDuration()!= null){
                        myMedia.setDuration(media.getDuration().toSeconds());
                        if ( media.getMetadata().get("length")!= null)
                            myMedia.setDuration((Double) media.getMetadata().get("length"));
                    }
                }
            });
        }
    }*/

}
