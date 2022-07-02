package it.unical.sadstudents.mediaplayeruid.model;

import it.unical.sadstudents.mediaplayeruid.thread.ThreadManager;
import it.unical.sadstudents.mediaplayeruid.view.SceneHandler;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;

import java.io.File;
import java.util.*;

public class Home {
    //VARIABLES
    private ArrayList<MyMedia> recentMedia ;
    private SimpleBooleanProperty changeHappened = new SimpleBooleanProperty(false);

    //SINGLETON AND CLASS DECLARATION
    private static Home instance = null;
    private Home (){
        recentMedia = new ArrayList<MyMedia>();
    }
    public static Home getInstance(){
        if (instance==null)
            instance = new Home();
        return instance;
    }
    //END SINGLETON

    //GETTERS
    public ArrayList<MyMedia> getRecentMedia() { return recentMedia; }

    public boolean isChangeHappened() {
        return changeHappened.get();
    }

    public SimpleBooleanProperty changeHappenedProperty() {
        return changeHappened;
    }

    public void setChangeHappened(boolean changeHappened) {
        this.changeHappened.set(changeHappened);
    }

//FUNCTIONS

    public void addMediaToPlayAndLibrary(List<File> files){
        /*for (int i=0; i<files.size(); i++){
            MyMedia myMedia = ThreadManager.getInstance().createMyMedia(files.get(i));
            if(i==0){
                PlayQueue.getInstance().generateNewQueue(myMedia);
            }
            else
                PlayQueue.getInstance().addFileToListFromOtherModel(myMedia);

            if (myMedia.getPath().toLowerCase().endsWith(".mp4")){
                VideoLibrary.getInstance().addFileToListFromOtherModel(myMedia);
            }
            else{
                MusicLibrary.getInstance().addFileToListFromOtherModel(myMedia);}
        }*/
        try {
            ThreadManager.getInstance().createMediaBis(files,true,true);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void addToRecentMedia(MyMedia myMedia){
        boolean added=false;
        for(int i=0; i<recentMedia.size();i++){
            if (myMedia.equals(recentMedia.get(i))){
                recentMedia.remove(i);
                recentMedia.add(myMedia);
                added=true;
            }
        }
        if(!added)
            recentMedia.add(myMedia);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                changeHappened.set(true);
            }
        });
    }

    //END FUNCTIONS



}
