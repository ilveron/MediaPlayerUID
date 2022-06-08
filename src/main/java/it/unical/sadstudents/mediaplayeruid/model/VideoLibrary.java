package it.unical.sadstudents.mediaplayeruid.model;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.util.ArrayList;


public class VideoLibrary{
    //VARIABLES
    private Integer videoNumber= 0; //numero di video presenti nella libreria
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
    public Integer getVideoNumber() {return videoNumber;}
    public ObservableList<MyMedia> getVideoLibrary() {return videoLibrary;}

    public void setVideoNumber(Integer videoNumber) {this.videoNumber = videoNumber;}



    //END GETTERS AND SETTERS




}
