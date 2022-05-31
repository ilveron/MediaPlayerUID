package it.unical.sadstudents.mediaplayeruid.model;

import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.media.Media;

import java.io.File;
import java.net.URL;
import java.util.*;

public class PlayQueue {

    private static PlayQueue instance = null;

    private PlayQueue (){
        queue = FXCollections.observableArrayList();
    }

    public static PlayQueue getInstance(){
        if (instance==null)
            instance = new PlayQueue();
        return instance;
    }

    private ObservableList<MyMedia> queue;
    private Integer currentMedia=0;

    public ObservableList<MyMedia> getQueue(){
        return queue;
    }

    public void generateNewQueue(File file){
        queue.clear();
        MyMedia media = new MyMedia(file);
        queue.add(media);

        //Player.getInstance().createMedia(queue.get(currentMedia));

    }

    public void generateNewQueueFromList(File[] files){
        queue.clear();
        for(File f: files){
            MyMedia media = new MyMedia(f);
            queue.add(media);
        }
    }

    public void addToQueue(MyMedia myMedia){
        queue.add(myMedia);
    }
}
