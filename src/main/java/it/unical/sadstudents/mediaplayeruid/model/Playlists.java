package it.unical.sadstudents.mediaplayeruid.model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;

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

    private SimpleIntegerProperty delete=new SimpleIntegerProperty(-1);
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

    public Integer getDelete() {return delete.get();}
    public SimpleIntegerProperty deleteProperty() {return delete;}
    public void setDelete(Integer delete) {this.delete.set(delete);}

    public void setPlayListsCollections(ObservableList<Playlist> playListsCollections) {
        this.playListsCollections = playListsCollections;
    }

    public int createNewPlaylist(){
        String image="file:"+"src/main/resources/it/unical/sadstudents/mediaplayeruid/image/iconaMusica.png";
        int pos=playListsCollections.size();
        Playlist playlist = new Playlist("Playlist"+playListsCollections.size(),image);
        playListsCollections.add(playlist);
        DatabaseManager.getInstance().createPlaylist(playlist.getName(),playlist.getImage());

        return pos;
    }

    public void deletePlaylist(int position){
        DatabaseManager.getInstance().deletePlaylist(playListsCollections.get(position).getName());
        playListsCollections.remove(position);
    }

    public void addPlaylist(Playlist playlist){
        playListsCollections.add(playlist);
    }
    public int returnPlaylist(String name){
        for(int i=0;i<playListsCollections.size();i++)
            if(playListsCollections.get(i).getName().equals(name))
                return i;
        return -1;
    }





}
