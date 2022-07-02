package it.unical.sadstudents.mediaplayeruid.thread;

import it.unical.sadstudents.mediaplayeruid.model.*;
import javafx.application.Platform;
import javafx.collections.MapChangeListener;
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


    private boolean next = false;
    private boolean startIsNeeded = false;
    private boolean resetPlayQueueNeeded = false;
    public void createMediaBis(List<File> files, boolean startIsNeeded,boolean resetPlayQueueNeeded) throws InterruptedException {
        this.startIsNeeded=startIsNeeded;
        this.resetPlayQueueNeeded=resetPlayQueueNeeded;
        Thread t = new Thread(() -> {
            MediaPlayer mediaPlayer;

            List<String> myMediaList = new ArrayList<>();
             for (int i=0; i<files.size();i++) {
                 next = false;
                 MyMedia myMedia = new MyMedia(files.get(i));
                 DatabaseManager.getInstance().addMedia(myMedia);
                 try {
                     String path = files.get(i).toURI().toString();
                     Media media = new Media(path);
                     media.getMetadata().addListener((MapChangeListener<String, Object>) change -> {
                         if (change.wasAdded()) {
                             String key = change.getKey();
                             //System.out.println(key);
                             if ("title".equals(key)) {
                                 if (media.getMetadata().get("title").toString() != null) {
                                     myMedia.setTitle(media.getMetadata().get("title").toString());
                                     myMediaList.add(myMedia.getTitle());
                                     myMediaList.add("Title");
                                     myMediaList.add(myMedia.getPath());
                                     //DatabaseManager.getInstance().setMediaString(myMedia.getTitle(),"Title", myMedia.getPath());
                                 }
                             } else if ("artist".equals(key)) {
                                 myMedia.setArtist(media.getMetadata().get("artist").toString());
                                 myMediaList.add(myMedia.getArtist());
                                 myMediaList.add("Artist");
                                 myMediaList.add(myMedia.getPath());
                                 //DatabaseManager.getInstance().setMediaString(myMedia.getArtist(),"Artist", myMedia.getPath());
                             } else if ("album".equals(key)) {
                                 myMedia.setAlbum(media.getMetadata().get("album").toString());
                                 myMediaList.add(myMedia.getAlbum());
                                 myMediaList.add("Album");
                                 myMediaList.add(myMedia.getPath());
                                 //DatabaseManager.getInstance().setMediaString(myMedia.getAlbum(),"Album", myMedia.getPath());
                             } else if ("genre".equals(key)) {
                                 myMedia.setGenre(media.getMetadata().get("genre").toString());
                                 myMediaList.add(myMedia.getGenre());
                                 myMediaList.add("Genre");
                                 myMediaList.add(myMedia.getPath());
                                 //DatabaseManager.getInstance().setMediaString(myMedia.getGenre(),"Genre", myMedia.getPath());
                             } else if ("year".equals(key)) {
                                 myMedia.setYear(media.getMetadata().get("year").toString());
                                 myMediaList.add(myMedia.getYear());
                                 myMediaList.add("Year");
                                 myMediaList.add(myMedia.getPath());
                                //DatabaseManager.getInstance().setMediaInt(myMedia.getYear(),"Year", myMedia.getPath());
                             }/* else if (media.getMetadata().get("duration") != null) {
                                 //myMedia.setLength(Double.parseDouble(media.getMetadata().get("duration").toString()));
                                 //DatabaseManager.getInstance().setMediaDouble(myMedia.getLength(),"Length", myMedia.getPath());
                                 System.out.println(media.getMetadata().get("duration").toString());
                             }*/

                             //System.out.println(media.getDuration());


                         }
                     });
                     mediaPlayer = new MediaPlayer(media);
                     MediaPlayer finalMediaPlayer = mediaPlayer;
                     mediaPlayer.setOnReady(new Runnable() {
                         @Override
                         public void run() {
                             myMedia.setLength(formatTime(finalMediaPlayer.getTotalDuration().toSeconds()));
                             myMediaList.add(myMedia.getLength());
                             myMediaList.add("Length");
                             myMediaList.add(myMedia.getPath());
                             finalMediaPlayer.dispose();
                         }
                     });

                     //mediaPlayer.dispose();

                     next = true;
                     if(startIsNeeded){
                         if (resetPlayQueueNeeded && i==0){
                             PlayQueue.getInstance().generateNewQueue(myMedia);
                         }
                         else{
                             PlayQueue.getInstance().addFileToListFromOtherModel(myMedia);
                         }
                     }
                     if (myMedia.getPath().toLowerCase().endsWith(".mp4")){
                         DatabaseManager.getInstance().setLibrary(myMedia.getPath(),"VideoLibrary");
                         VideoLibrary.getInstance().addFileToListFromOtherModel(myMedia);
                     }
                     else{
                         DatabaseManager.getInstance().setLibrary(myMedia.getPath(),"MusicLibrary");
                         MusicLibrary.getInstance().addFileToListFromOtherModel(myMedia);
                     }
                 } catch (Exception e) {
                     next = true;
                 }
                 while(!next) {}
             }

            synchronized(obj){
                try {
                    obj.wait(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            for (int i=0; i<myMediaList.size();i+=3 ){
                 next= false;
                 try {
                     DatabaseManager.getInstance().setMediaString(myMediaList.get(i), myMediaList.get(i + 1), myMediaList.get(i + 2));
                     next = true;
                 }catch (Exception exception){
                     next=true;
                 }


             }
             while(!next){}



         });
         t.setDaemon(true);
         t.start();
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

    public String formatTime(double timeDouble) {
        if (timeDouble > 0) {
            int hh = (int) (timeDouble / 3600);
            int mm = (int) ((timeDouble % 3600) / 60);
            int ss = (int) ((timeDouble % 3600) % 60);

            return String.format("%02d:%02d:%02d", hh, mm, ss);
        }

        return "00:00:00";
    }
}
