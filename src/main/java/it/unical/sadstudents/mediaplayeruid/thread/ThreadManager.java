package it.unical.sadstudents.mediaplayeruid.thread;

import it.unical.sadstudents.mediaplayeruid.model.*;
import it.unical.sadstudents.mediaplayeruid.view.SceneHandler;
import javafx.application.Platform;
import javafx.collections.MapChangeListener;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.SnapshotResult;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Callback;
import javafx.util.Duration;

import java.io.File;
import java.sql.ResultSet;
import java.util.*;
import java.util.function.ToDoubleBiFunction;

import static java.lang.String.format;
import static java.lang.System.currentTimeMillis;
import static java.lang.Thread.sleep;

public class ThreadManager {
    private Timer timer;
    private TimerTask timerTask;
    //private Task task;
    //private Thread thread;
    private final Object obj= new Object();

    private double mediaFinded = 0.0;
    private double mediaProcessed = 0.0;
    private double metaDataFinded = 0.0;
    private double metaDataProcessed=0.0;



    /*public Thread getThread() {
        return thread;
    }*/

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



    public void addToLibraryFromDB(ResultSet rs){
        Thread A = new Thread(()->{

        });
        A.setDaemon(true);
        A.start();

    }


    private boolean next = false;
    private boolean startIsNeeded = false;
    private boolean resetPlayQueueNeeded = false;
    public void createMediaBis(List<File> files, boolean startIsNeeded,boolean resetPlayQueueNeeded) throws InterruptedException {
        this.startIsNeeded=startIsNeeded;
        this.resetPlayQueueNeeded=resetPlayQueueNeeded;
        mediaFinded=files.size();
        SceneHandler.getInstance().setMediaLoadingInProgess(true);
        Thread t = new Thread(() -> {
            int cont=0;
            double start = System.currentTimeMillis();
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
                             }


                         }
                     });

                     mediaPlayer = new MediaPlayer(media);
                     MediaPlayer finalMediaPlayer = mediaPlayer;
                     finalMediaPlayer.setOnReady(new Runnable() {
                         @Override
                         public void run() {
                             myMedia.setLength(formatTime(finalMediaPlayer.getTotalDuration().toSeconds()));
                             myMediaList.add(myMedia.getLength());
                             myMediaList.add("Length");
                             myMediaList.add(myMedia.getPath());
                             finalMediaPlayer.dispose();
                         }
                     });
                     boolean tooTime=false;
                     double current=System.currentTimeMillis();
                     while(!tooTime && finalMediaPlayer.getStatus()!= MediaPlayer.Status.READY ){
                         if(myMedia.getPath().toLowerCase().endsWith(".mp3") && System.currentTimeMillis()-current>50) //con 100 solo 3 persi
                             tooTime=true;
                         else if(myMedia.getPath().toLowerCase().endsWith(".mp4") && System.currentTimeMillis()-current>5000)
                             tooTime=true;

                     }

                     //finalMediaPlayer.ready

                     //System.out.println(String.format(("%d %d"),files.size(),++cont));



                     mediaProcessed+=1;
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
                     mediaProcessed+=1;
                 }
                 while(!next) {}
             }
            try {
                sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            metaDataFinded= myMediaList.size()/3;
            Platform.runLater(() -> {
                SceneHandler.getInstance().setMetadataLoadindagInProgess(true);
            });

            for (int i=0; i<myMediaList.size();i+=3 ){
                 next= false;
                 try {
                     DatabaseManager.getInstance().setMediaString(myMediaList.get(i), myMediaList.get(i + 1), myMediaList.get(i + 2));
                     metaDataProcessed+=1;
                     next = true;
                 }catch (Exception exception){
                     metaDataProcessed+=1;
                     next=true;
                 }

                while(!next){}
            }

            double fine = System.currentTimeMillis();
            System.out.println (String.format("tempo impiegato %f",(fine-start)));




         });
         t.setDaemon(true);
         t.start();
    }




    //public void startUpdateProgress(ProgressBar)

    public void progressBarUpdate(ProgressBar progressBar,String type) {
       Task task = new Task<Void>() {
            @Override
            public Void call() {
                if(type=="media"){
                    while(mediaFinded>mediaProcessed) {
                        if(isCancelled())
                            break;
                        updateProgress(mediaProcessed,mediaFinded);
                    }
                }else{
                    while(metaDataFinded>metaDataProcessed) {
                        if(isCancelled())
                            break;
                        updateProgress(metaDataProcessed,metaDataFinded);
                        //break;
                    }
                    SceneHandler.getInstance().setMetadataLoadindagInProgess(false);
                    SceneHandler.getInstance().setMediaLoadingInProgess(false);
                    System.out.println("finito");
                }
                return null;
            }
        };
        progressBar.progressProperty().bind(task.progressProperty());
        Thread t = new Thread(task);
        t.setDaemon(true);
        t.start();

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

            return format("%02d:%02d:%02d", hh, mm, ss);
        }

        return "00:00:00";
    }


}
