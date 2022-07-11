package it.unical.sadstudents.mediaplayeruid.model;


import it.unical.sadstudents.mediaplayeruid.utils.ListedDataInterface;
import it.unical.sadstudents.mediaplayeruid.utils.ThreadManager;
import it.unical.sadstudents.mediaplayeruid.view.VideoGalleryTilePaneHandler;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.util.List;


public class VideoLibrary implements ListedDataInterface {
    //VARIABLES
    private ObservableList<MyMedia> videoLibrary;
    private SimpleBooleanProperty changeHappened = new SimpleBooleanProperty(false);


    //SINGLETON AND CLASS DECLARATION
    private static VideoLibrary instance = null;

    private VideoLibrary (){
        videoLibrary = FXCollections.observableArrayList();


        /*BooleanBinding multipleElemsProperty = new BooleanBinding() {
            @Override protected boolean computeValue() {
                return videoLibrary.size() > 1;
            }
        };*/
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
        VideoGalleryTilePaneHandler.getInstance().addSingleItem();

    }

    @Override
    public void addFilesToList(List<File> files) {
        /*boolean isPresent = false;
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
            setChangeHappened(true);*/

        try {
            ThreadManager.getInstance().createMediaBis(files,false,false);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void removeSafe(MyMedia myMedia){

        deleteCheck(myMedia);
        VideoGalleryTilePaneHandler.getInstance().removeWithIndex(myMedia);
        DatabaseManager.getInstance().deleteMedia(myMedia.getPath(),"MyMedia");
        videoLibrary.remove(myMedia);
       // changeHappened.set(true);


        // TODO: 06/07/2022 delete su playqueue e su playlist DA VEDERE
    }

    public void clearAll(){
        PlayQueue.getInstance().setDeletingInProcess(true);
        for (int i=0;i<videoLibrary.size();i++){
            deleteCheck(videoLibrary.get(i));
        }

        videoLibrary.clear();
        VideoGalleryTilePaneHandler.getInstance().getMyMediaSingleBoxes().clear();
        VideoGalleryTilePaneHandler.getInstance().setReadyInteger(0);
        DatabaseManager.getInstance().deleteAllLibrary("VideoLibrary");
        PlayQueue.getInstance().canRestart();
    }

    public void deleteCheck(MyMedia myMedia){
        Home.getInstance().removeItem(myMedia);
        PlayQueue.getInstance().deleteFromOtherController(myMedia);
        PlaylistCollection.getInstance().deleteMediaCompletely(myMedia.getPath());
    }


    //END GETTERS AND SETTERS




}
