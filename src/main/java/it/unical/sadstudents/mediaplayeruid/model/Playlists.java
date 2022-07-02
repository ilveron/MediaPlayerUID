package it.unical.sadstudents.mediaplayeruid.model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
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

    private SimpleBooleanProperty playing=new SimpleBooleanProperty(false);
    private SimpleStringProperty typePlaylist=new SimpleStringProperty("");
    private SimpleBooleanProperty update=new SimpleBooleanProperty(false);

    public ObservableList<Playlist> getPlayListsCollections() {
        return playListsCollections;
    }

    public boolean isPlaying() {return playing.get();}
    public SimpleBooleanProperty playingProperty() {return playing;}
    public void setPlaying(boolean playing) {this.playing.set(playing);}

    public String getTypePlaylist() {return typePlaylist.get();}
    public SimpleStringProperty typePlaylistProperty() {return typePlaylist;}
    public void setTypePlaylist(String typePlaylist) {this.typePlaylist.set(typePlaylist);}

    public boolean isUpdate() {return update.get();}
    public SimpleBooleanProperty updateProperty() {return update;}
    public void setUpdate(boolean update) {this.update.set(update);}

    public void setPlayListsCollections(ObservableList<Playlist> playListsCollections) {
        this.playListsCollections = playListsCollections;
    }

    public void createNewPlayList(){
        Playlist playlist = new Playlist("ciao","");
        playListsCollections.add(playlist);
    }
}
