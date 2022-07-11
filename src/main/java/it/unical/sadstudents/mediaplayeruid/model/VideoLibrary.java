package it.unical.sadstudents.mediaplayeruid.model;


import it.unical.sadstudents.mediaplayeruid.utils.ListedDataInterface;
import it.unical.sadstudents.mediaplayeruid.utils.MyNotification;
import it.unical.sadstudents.mediaplayeruid.utils.ThreadManager;
import it.unical.sadstudents.mediaplayeruid.view.VideoGalleryTilePaneHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.util.List;


public class VideoLibrary implements ListedDataInterface {
    //VARIABLES
    private ObservableList<MyMedia> videoLibrary;

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
    //END

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
        try {
            ThreadManager.getInstance().createMediaBis(files,false,false);
        } catch (InterruptedException e) {
            MyNotification.notifyWarning("","Error",3);
        }
    }

    public void removeSafe(MyMedia myMedia){
        deleteCheck(myMedia);
        VideoGalleryTilePaneHandler.getInstance().removeWithIndex(myMedia);
        DatabaseManager.getInstance().deleteMedia(myMedia.getPath(),"MyMedia");
        videoLibrary.remove(myMedia);
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
