package it.unical.sadstudents.mediaplayeruid.model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.Objects;

import static java.lang.String.format;

public class Playlist  {

    private ObservableList<MyMedia> list;
    private String image;
    private String name;
    private Integer songs;
    private String totalDuration;
    private SimpleBooleanProperty playing=new SimpleBooleanProperty(false);
    private boolean initialized=false;

    public boolean isInitialized() {
        return initialized;
    }

    public void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }

    public boolean isPlaying() {
        return playing.get();
    }

    public SimpleBooleanProperty playingProperty() {
        return playing;
    }

    public void setPlaying(boolean playing) {
        this.playing.set(playing);
    }

    public Playlist(String name, String image, Integer songs, String totalDuration){
        list= FXCollections.observableArrayList();
        this.image=image;
        this.name=name;
        this.songs=songs;
        this.totalDuration=totalDuration;
        listenerRefresh();

    }
    public Playlist(){
        listenerRefresh();
    }

    private void listenerRefresh(){
        PlaylistCollection.getInstance().updatePlayQueueProperty().addListener(observable -> {
            if(PlaylistCollection.getInstance().getTypePlaylist().equals(getName())  && PlaylistCollection.getInstance().getUpdatePlayQueue()) {
                if(getMyList().size()>0) {
                    refreshPlayQueue();
                }
            }
        });
    }

    public void addMedia(MyMedia myMedia){
        songs++;
        durationCalculation(myMedia.getLength());
        list.add(myMedia);
        if(isPlaying())
            PlayQueue.getInstance().addFileToListFromOtherModel(myMedia);
        DatabaseManager.getInstance().setPlaylistSong(getSongs(), getTotalDuration(),getName());
        DatabaseManager.getInstance().addMyMediaInPlaylist(myMedia.getPath(), getName(),list.size()-1);

    }
    public void clearSongs(){
        songs=0;
        totalDuration="00:00:00";
    }
    public void add(MyMedia myMedia){
        list.add(myMedia);
    }
    public ObservableList<MyMedia> getMyList(){return list;}

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


    public Integer getSongs() {return songs;}
    public void setSongs(Integer songs) {this.songs = songs;}

    public String getTotalDuration() {return totalDuration;}
    public void setTotalDuration(String totalDuration) {this.totalDuration = totalDuration;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Playlist playlist = (Playlist) o;
        return Objects.equals(list, playlist.list) && Objects.equals(image, playlist.image) && Objects.equals(name, playlist.name);
    }

    public void deleteMyMedia(String path){
        boolean eliminate=false;
        for(int i=0;i<list.size();i++){
            if(path.equals(list.get(i).getPath())) {
                songs--;
                if(songs==0)
                    totalDuration="00:00:00";
                else
                    durationCalculationRemove(list.get(i).getLength());
                DatabaseManager.getInstance().setPlaylistSong(songs,totalDuration,name);
                eliminate=true;
                list.remove(i);
                --i;
                //Playlists.getInstance().setRefresh(1);
            }
        }
        if(eliminate)
            DatabaseManager.getInstance().deleteMedia(path,"MyMediaPlaylist");

    }

    public int indexMedia(MyMedia myMedia){
        for(int i=0;i<list.size();i++){
            if(myMedia.equals(list.get(i)))
                return i;
        }
        return -1;
    }

    private void durationCalculation(String duration){



        int hour=Integer.parseInt(duration.substring(0,2));
        int minute=Integer.parseInt(duration.substring(3,5));
        int second=Integer.parseInt(duration.substring(6,8));

        int h=Integer.parseInt(getTotalDuration().substring(0,2));
        int m=Integer.parseInt(getTotalDuration().substring(3,5));
        int s=Integer.parseInt(getTotalDuration().substring(6,8));


        int ts=(s+second)%60;
        int tm=(m+minute+((s+second)/60))%60;
        int to=(h+hour+((m+minute+((s+second)/60)))/60);

        setTotalDuration(format("%02d:%02d:%02d",to,tm,ts));

    }
    private void durationCalculationRemove(String duration){
        // TODO: 07/07/2022  da controllare 
        int hour=Integer.parseInt(duration.substring(0,2));
        int minute=Integer.parseInt(duration.substring(3,5));
        int second=Integer.parseInt(duration.substring(6,8));

        int h=Integer.parseInt(getTotalDuration().substring(0,2));
        int m=Integer.parseInt(getTotalDuration().substring(3,5));
        int s=Integer.parseInt(getTotalDuration().substring(6,8));

        int ts=0,to=0,tm = 0;
        if(!(h<hour)) {
            if (s < second) {
                ts = second - s;
                minute++;
            } else
                ts = s - second;

            if (m < minute) {
                tm = minute - m;
                hour++;
            } else
                tm = m - minute;
            if (h >= hour) {
                to = h - hour;
            } else {
                to=0;ts=0;tm=0;
            }
        }
        setTotalDuration(format("%02d:%02d:%02d",to,tm,ts));

        /*if(to>=100)
            playlist.setTotalDuration(format("+%02d:%02d:%02d",to,tm,ts));
        else
            playlist.setTotalDuration(format("%02d:%02d:%02d",to,tm,ts));

         */

    }

    public void addToPlayQueue(MyMedia myMedia){
        int index=indexMedia(myMedia);
        PlayQueue.getInstance().clearList();
        for(int i=0;i<list.size();i++){
            PlayQueue.getInstance().addFileToListFromOtherModel(list.get(index));
            index=(index+1)%list.size();
        }
    }

    private void refreshPlayQueue(){
        for(int i=0;i<getMyList().size();i++){
            boolean e=false;
            for(int j=0;j<PlayQueue.getInstance().getQueue().size();j++)
                if(getMyList().get(i).equals(PlayQueue.getInstance().getQueue().get(j)) )
                    e=true;
            if(!e)
                PlayQueue.getInstance().addFileToListFromOtherModel(getMyList().get(i));
        }
        PlaylistCollection.getInstance().setUpdatePlayQueue(false);
    }

    public void playAll(boolean simpleAdd){
        for(int i=0; i<list.size(); i++){
            if(!simpleAdd && i==0 && !playing.get())
                PlayQueue.getInstance().generateNewQueue(list.get(i));

            else
                PlayQueue.getInstance().addFileToListFromOtherModel(list.get(i));
        }

    }

    public void clearMyList(){
        DatabaseManager.getInstance().deleteMediaPlaylist(name,"Name");
        list.clear();
    }






}

