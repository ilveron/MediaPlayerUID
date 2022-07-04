package it.unical.sadstudents.mediaplayeruid.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataExchangePlaylist {
    private String image="";
    private String name="";
    private ObservableList<MyMedia> list;

    private static DataExchangePlaylist instance = null;
    private DataExchangePlaylist(){
        list = FXCollections.observableArrayList();
    }
    public static DataExchangePlaylist getInstance(){
        if (instance==null)
            instance = new DataExchangePlaylist();
        return instance;
    }
    public void addPlaylist(MyMedia m){
        for (MyMedia myMedia1: list){
            if (m.equals(myMedia1))
                return;
        }
        list.add(m);
        DatabaseManager.getInstance().addMyMediaInPlaylist(m.getPath(),name);
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ObservableList<MyMedia> getList() {
        return list;
    }

    public void setList(ObservableList<MyMedia> list) {
        this.list = list;
    }
}
