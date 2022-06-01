package it.unical.sadstudents.mediaplayeruid.model;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

public class Home {

    private static Home instance = null;

    private Home (){}

    private Home getInstance(){
        if (instance==null)
            instance = new Home();
        return instance;
    }

    private ArrayList<MyMedia> recentMedia = new ArrayList<MyMedia>(10);

    public void addToRecent(MyMedia myMedia){
        if(recentMedia.size()>=10){
            recentMedia.remove(0);
        }
        recentMedia.add(myMedia);
    }



}
