package it.unical.sadstudents.mediaplayeruid.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import static java.lang.String.format;

public class DataExchangePlaylist {

    private Playlist playlist;


    private static DataExchangePlaylist instance = null;
    private DataExchangePlaylist(){
        playlist=new Playlist();
    }
    public static DataExchangePlaylist getInstance(){
        if (instance==null)
            instance = new DataExchangePlaylist();
        return instance;
    }
    public void addPlaylist(MyMedia m){
        for (MyMedia myMedia1: playlist.getMyList()){
            if (m.equals(myMedia1))
                return;
        }
        playlist.addMedia(m);
    }




    public void setPlaylist(Playlist playlist){this.playlist=playlist;}
    public Playlist getPlaylist(){return playlist;}

    public String getImage() {
        return playlist.getImage();
    }
    public void setImage(String image) {playlist.setImage( image);}

    public String getName() {return playlist.getName();}
    public void setName(String name) {playlist.setName(name);}

    public ObservableList<MyMedia> getList() {
        return getPlaylist().getMyList();
    }

    /*public void setList(ObservableList<MyMedia> list) {
        playlist.set = list;
    }*/
}
