package it.unical.sadstudents.mediaplayeruid.model;

import it.unical.sadstudents.mediaplayeruid.utils.MyNotification;
import it.unical.sadstudents.mediaplayeruid.utils.ThreadManager;
import it.unical.sadstudents.mediaplayeruid.view.HomeTilePaneHandler;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;

import java.io.File;
import java.util.*;

public class Home {
    //VARIABLES
    private ArrayList<MyMedia> recentMedia ;
    private SimpleBooleanProperty changeHappened = new SimpleBooleanProperty(false);
    private SimpleBooleanProperty loading = new SimpleBooleanProperty(true);

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


//FUNCTIONS

    public void addMediaToPlayAndLibrary(List<File> files){
        try {
            ThreadManager.getInstance().createMediaBis(files,true,true);
        } catch (InterruptedException e) {
            MyNotification.notifyWarning("","Error",3);
        }
    }

    public void addToRecentMedia(MyMedia myMedia){
        boolean added=false;
        for(int i=0; i<recentMedia.size() && !added;i++){
            if (myMedia.equals(recentMedia.get(i)) ){
                try{
                    recentMedia.remove(i);
                }catch (Exception exception){
                    MyNotification.notifyError("","Remove error",3);
                }
                recentMedia.add(myMedia);
                added=true;
                HomeTilePaneHandler.getInstance().moveWithIndex(i);
            }
        }
        if(!added ) {
            if(recentMedia.size()>=20){
                DatabaseManager.getInstance().deleteMedia(recentMedia.get(0).getPath(),"RecentMedia");
                try{
                    recentMedia.remove(0);
                    HomeTilePaneHandler.getInstance().removeWithIndex(0);
                }
                catch (Exception exception){
                    MyNotification.notifyError("","Remove error",3);
                }
            }
            recentMedia.add(myMedia);
            HomeTilePaneHandler.getInstance().addSingleItem();
        }
        DatabaseManager.getInstance().insertRecentMedia(myMedia);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                changeHappened.set(true);
            }
        });
    }

    public void addToRecentMediaBis(MyMedia myMedia){
        recentMedia.add(myMedia);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                changeHappened.set(true);
            }
        });
    }

    public void removeItem(MyMedia myMedia){
        for (int i = 0; i < Home.getInstance().getRecentMedia().size(); i++) {
            if (myMedia.equals(Home.getInstance().getRecentMedia().get(i))) {
                DatabaseManager.getInstance().deleteMedia(recentMedia.get(i).getPath(),"RecentMedia");
                try{
                    recentMedia.remove(i);
                    HomeTilePaneHandler.getInstance().removeWithIndex(i);
                }
                catch (Exception exception)
                {
                    MyNotification.notifyError("","Remove error",3);
                }
                break;
            }
        }
    }
    //END FUNCTIONS

}
