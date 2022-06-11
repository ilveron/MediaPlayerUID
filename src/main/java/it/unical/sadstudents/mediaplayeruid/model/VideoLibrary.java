package it.unical.sadstudents.mediaplayeruid.model;


import it.unical.sadstudents.mediaplayeruid.thread.ThreadManager;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.util.List;


public class VideoLibrary implements DataListedModel{
    //VARIABLES
    private ObservableList<MyMedia> videoLibrary;
    private SimpleBooleanProperty changeHappened = new SimpleBooleanProperty(false);

    //SINGLETON AND CLASS DECLARATION
    private static VideoLibrary instance = null;

    private VideoLibrary (){
        videoLibrary = FXCollections.observableArrayList();
    }

    public static VideoLibrary getInstance(){
        if (instance==null)
            instance = new VideoLibrary();
        return instance;
    }
    //END SINGLETON


    //GETTERS AND SETTERS

    public ObservableList<MyMedia> getVideoLibrary() {
        return videoLibrary;
    }

    public void setVideoLibrary(ObservableList<MyMedia> videoLibrary) {
        this.videoLibrary = videoLibrary;
    }

    public boolean isChangeHappened() {
        return changeHappened.get();
    }

    public SimpleBooleanProperty changeHappenedProperty() {
        return changeHappened;
    }

    public void setChangeHappened(boolean changeHappened) {
        this.changeHappened.set(changeHappened);
    }

    @Override
    public void clearList() {
        videoLibrary.clear();
    }

    @Override
    public void addFileToListFromOtherModel(MyMedia myMedia) {
        for (MyMedia myMedia1: videoLibrary){
            if (myMedia.equals(myMedia1))
                return;
        }
        videoLibrary.add(myMedia);
        setChangeHappened(true);

    }

    @Override
    public void addFilesToList(List<File> files) {
        boolean isPresent = false;
        boolean almostOneAdded = false;
        for (File file: files){
            MyMedia myMedia = ThreadManager.getInstance().createMyMedia(file);
            for (int i=0; i< videoLibrary.size() && !isPresent;i++){
                if(myMedia.equals(videoLibrary.get(i))){
                    isPresent = true;
                }
            }
            if (!isPresent){
                videoLibrary.add(myMedia);
                almostOneAdded = true;
            }
            isPresent=false;
        }
        if(almostOneAdded)
            setChangeHappened(true);

    }


    //END GETTERS AND SETTERS




}
