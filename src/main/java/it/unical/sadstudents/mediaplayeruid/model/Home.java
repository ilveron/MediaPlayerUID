package it.unical.sadstudents.mediaplayeruid.model;

import java.util.ArrayList;

public class Home {
    //VARIABLES
    private ArrayList<MyMedia> recentMedia = new ArrayList<MyMedia>(10);

    //SINGLETON AND CLASS DECLARATION
    private static Home instance = null;
    private Home (){}
    private Home getInstance(){
        if (instance==null)
            instance = new Home();
        return instance;
    }
    //END SINGLETON

    //GETTERS
    public ArrayList<MyMedia> getRecentMedia() { return recentMedia; }



    //FUNCTIONS
    public void addToRecent(MyMedia myMedia){
        if(recentMedia.size()>=10){
            recentMedia.remove(0);
        }
        recentMedia.add(myMedia);
    }

    //END FUNCTIONS



}
