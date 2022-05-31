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

    private ObservableList<File> queue;
    private Integer currentMedia=0;



    public void generateNewQueue(File file){
        queue.clear();
        queue.add(file);
        Player.getInstance().createMedia(queue.get(currentMedia));

    }

    public void addToQueue(File file){
        queue.add(file);
    }



}
