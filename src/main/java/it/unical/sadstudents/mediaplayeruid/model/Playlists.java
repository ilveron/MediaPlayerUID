package it.unical.sadstudents.mediaplayeruid.model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
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

    private SimpleIntegerProperty delete=new SimpleIntegerProperty(-1);
    private SimpleBooleanProperty playing=new SimpleBooleanProperty(false);
    private SimpleStringProperty typePlaylist=new SimpleStringProperty("");
    private SimpleBooleanProperty updatePlayQueue =new SimpleBooleanProperty(false);

    public ObservableList<Playlist> getPlayListsCollections() {
        return playListsCollections;
    }




    public boolean isPlaying() {return playing.get();}
    public SimpleBooleanProperty playingProperty() {return playing;}
    public void setPlaying(boolean playing) {this.playing.set(playing);}

    public String getTypePlaylist() {return typePlaylist.get();}
    public SimpleStringProperty typePlaylistProperty() {return typePlaylist;}
    public void setTypePlaylist(String typePlaylist) {this.typePlaylist.set(typePlaylist);}

    public boolean getUpdatePlayQueue() {return updatePlayQueue.get();}
    public SimpleBooleanProperty updatePlayQueueProperty() {return updatePlayQueue;}
    public void setUpdatePlayQueue(boolean updatePlayQueue) {this.updatePlayQueue.set(updatePlayQueue);}

    public Integer getDelete() {return delete.get();}
    public SimpleIntegerProperty deleteProperty() {return delete;}
    public void setDelete(Integer delete) {this.delete.set(delete);}

    public void setPlayListsCollections(ObservableList<Playlist> playListsCollections) {
        this.playListsCollections = playListsCollections;
    }

    public int createNewPlaylist(){
        String image="file:"+"src/main/resources/it/unical/sadstudents/mediaplayeruid/image/iconaMusica.png";
        int pos=playListsCollections.size()+1;
        String name="Playlist"+pos;
        while(getPlaylistWidthName(name)!=-1) {
            pos++;
            name = "Playlist" + pos;
        }

        pos=playListsCollections.size();

        Playlist playlist = new Playlist(name,image,0,"00:00:00");
        playListsCollections.add(playlist);
        DatabaseManager.getInstance().createPlaylist(playlist.getName(),playlist.getImage(),0,"00:00:00");
        return pos;
    }

    public void deletePlaylist(int position){
        DatabaseManager.getInstance().deletePlaylist(playListsCollections.get(position).getName());
        playListsCollections.remove(position);
    }

    public int getPlaylistWidthName(String name){
        for(int i=0;i<playListsCollections.size();i++){
            if(playListsCollections.get(i).getName().equals(name))
                return i;
        }
        return -1;
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

    public void deleteMediaCompletely(String Path){
        for(int i=0;i<Playlists.getInstance().getPlayListsCollections().size();i++){
            Playlists.getInstance().getPlayListsCollections().get(i).deleteMyMedia(Path);
        }
        //setRefresh(1);
    }





}
