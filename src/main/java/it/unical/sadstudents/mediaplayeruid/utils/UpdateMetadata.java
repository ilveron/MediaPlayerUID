package it.unical.sadstudents.mediaplayeruid.utils;

import it.unical.sadstudents.mediaplayeruid.model.*;
import it.unical.sadstudents.mediaplayeruid.view.SceneHandler;
import javafx.application.Platform;
import javafx.collections.MapChangeListener;
import javafx.scene.media.Media;

public class UpdateMetadata {
    private MyMedia myMedia;
    private Media media;

    //SINGLETON
    private static UpdateMetadata instance = null;
    private UpdateMetadata() {  Player.getInstance().endMediaTimeProperty().addListener(observable -> {
        updateTime();
    });
    }
    public static UpdateMetadata getInstance(){
        if (instance==null)
            instance = new UpdateMetadata();

        return instance;
    }
    //END SINGLETON

    public void updateMetadata(MyMedia myMedia, Media media){
        this.myMedia = myMedia;
        this.media = media;
        media.getMetadata().addListener((MapChangeListener<String, Object>) change -> {
            if (change.wasAdded()) {
                String key = change.getKey();
                if ("title".equals(key)) {
                    if (media.getMetadata().get("title").toString() != null) {
                        myMedia.setTitle(media.getMetadata().get("title").toString());
                        DatabaseManager.getInstance().setMediaString(myMedia.getTitle(),"Title", myMedia.getPath());
                    }
                } else if ("artist".equals(key)) {
                    myMedia.setArtist(media.getMetadata().get("artist").toString());
                    DatabaseManager.getInstance().setMediaString(myMedia.getArtist(),"Artist", myMedia.getPath());
                } else if ("album".equals(key)) {
                    myMedia.setAlbum(media.getMetadata().get("album").toString());
                    DatabaseManager.getInstance().setMediaString(myMedia.getAlbum(),"Album", myMedia.getPath());
                } else if ("genre".equals(key)) {
                    myMedia.setGenre(media.getMetadata().get("genre").toString());
                    DatabaseManager.getInstance().setMediaString(myMedia.getGenre(),"Genre", myMedia.getPath());
                } else if ("year".equals(key)) {
                    myMedia.setYear(media.getMetadata().get("year").toString());
                    DatabaseManager.getInstance().setMediaString(myMedia.getYear(),"Year", myMedia.getPath());
                }
            }
        });
    }

    public void updateTime(){

        if(Player.getInstance().getEndMediaTime()>0){
            myMedia.setLength(ThreadManager.getInstance().formatTime(Player.getInstance().getEndMediaTime()));
            if(myMedia.getPath().toLowerCase().endsWith(".mp4")){
                for(int i = 0; i< VideoLibrary.getInstance().getVideoLibrary().size(); i++){
                    if(myMedia.equals(VideoLibrary.getInstance().getVideoLibrary().get(i)) && !myMedia.getLength().equals(VideoLibrary.getInstance().getVideoLibrary().get(i).getLength()))
                        VideoLibrary.getInstance().getVideoLibrary().get(i).setLength(myMedia.getLength());
                }
            }
            else{
                for(int i=0; i< MusicLibrary.getInstance().getMusicLibrary().size();i++){
                    if(myMedia.equals(MusicLibrary.getInstance().getMusicLibrary().get(i)) && !myMedia.getLength().equals(MusicLibrary.getInstance().getMusicLibrary().get(i).getLength()))
                        MusicLibrary.getInstance().getMusicLibrary().get(i).setLength(myMedia.getLength());
                }
            }


            DatabaseManager.getInstance().setMediaString(myMedia.getLength(),"Length", myMedia.getPath());
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    SceneHandler.getInstance().setUpdateViewRequired(true);
                }
            });


        }

    }

}
