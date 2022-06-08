package it.unical.sadstudents.mediaplayeruid.model;

import it.unical.sadstudents.mediaplayeruid.ThreadManager;
import javafx.beans.property.SimpleIntegerProperty;

import java.io.File;
import java.util.*;

public class Home {
    //VARIABLES
    private ArrayList<MyMedia> recentMedia ;
    private SimpleIntegerProperty sizeRecentMedia = new SimpleIntegerProperty(0);

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

    public int getSizeRecentMedia() { return sizeRecentMedia.get(); }

    public SimpleIntegerProperty sizeRecentMediaProperty() { return sizeRecentMedia;     }

    public void setSizeRecentMedia(int sizeRecentMedia) { this.sizeRecentMedia.set(sizeRecentMedia);     }

    //FUNCTIONS
    public void addToRecent(MyMedia myMedia){
        recentMedia.add(myMedia);
        sizeRecentMedia.add(1);

    }

    public void addMediaToPlayAndLibrary(List<File> files){
        for (int i=0; i<files.size(); i++){
            MyMedia myMedia = ThreadManager.getInstance().createMyMedia(files.get(i));
            if(i==0){
                PlayQueue.getInstance().generateNewQueue(myMedia);
            }
            else
                PlayQueue.getInstance().addFileToListFromOtherModel(myMedia);

            if (myMedia.getPath().toLowerCase().endsWith(".mp4")){
                System.out.println("ciao");
            }
            else{
                MusicLibrary.getInstance().addFileToListFromOtherModel(myMedia);}
        }

    }

    public void addToRecentMedia(MyMedia myMedia){
        recentMedia.add(myMedia);
    }

    //END FUNCTIONS



}
