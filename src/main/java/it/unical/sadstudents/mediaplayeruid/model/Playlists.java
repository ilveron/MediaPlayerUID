package it.unical.sadstudents.mediaplayeruid.model;

import it.unical.sadstudents.mediaplayeruid.model.Playlist;
import it.unical.sadstudents.mediaplayeruid.view.PlayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Playlists {

    private ObservableList<Playlist> playListsCollections;

    //SINGLETON
    private static Playlists instance = null;
    private Playlists (){
        playListsCollections = FXCollections.observableArrayList();
    }
    public static Playlists getInstance(){
        if (instance==null)
            instance = new Playlists();
        return instance;
    }
    //END SINGLETON


    public ObservableList<Playlist> getPlayListsCollections() {
        return playListsCollections;
    }

    public void setPlayListsCollections(ObservableList<Playlist> playListsCollections) {
        this.playListsCollections = playListsCollections;
    }

    public void createNewPlayList(){
        Playlist playlist = new Playlist("ciao","");
        playListsCollections.add(playlist);
    }
}
