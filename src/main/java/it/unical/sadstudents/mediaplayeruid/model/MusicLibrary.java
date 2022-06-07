package it.unical.sadstudents.mediaplayeruid.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.util.ArrayList;

public class MusicLibrary implements DataListedModel{
    //VARIABLES
    private ObservableList<MyMedia> Library;
    private Integer KMusic=0;

    //SINGLETON AND CLASS DECLARATION
    private static MusicLibrary instance = null;
    private MusicLibrary (){
        Library = FXCollections.observableArrayList();
    }
    public static MusicLibrary getInstance(){
        if (instance==null)
            instance = new MusicLibrary();
        return instance;
    }
    //END SINGLETON

    //GETTERS
    public ObservableList<MyMedia> getMusicLibrary() {
        return Library;
    }
    public Integer getKMusic() {
        return KMusic;
    }


    //FUNCTIONS
    @Override
    public void clearList() {
        Library.clear();
    }

    @Override
    public void addFileToList(File file) {
        MyMedia myMedia = new MyMedia(file);
        Library.add(myMedia);
        ++KMusic;
  }

    @Override
    public void addFolderToList(ArrayList<File> files) {
        for(File file: files){
            MyMedia myMedia = new MyMedia(file);
            Library.add(myMedia);
            ++KMusic;
        }
    }
    //END FUNCTIONS

}
