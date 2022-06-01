package it.unical.sadstudents.mediaplayeruid.model;

import javafx.collections.MapChangeListener;
import javafx.scene.media.Media;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

public class MyMedia {
    //private Media media;
    private String title = "";
    private String artist = "";
    private String album = "";
    private String genre = "";
    //private File file;
    private String path = "";
    private Double duration = 0.0;
    private Integer releaseYear = 0;


    private boolean isReturnable = false;

    public MyMedia() { }

    public MyMedia(File file) {
        path = file.toURI().toString();
        setTitle(file.getName());
        Media media = new Media(path);

        media.getMetadata().addListener((MapChangeListener<String, Object>) change -> {
            if(change.wasAdded()){
                if("title".equals(change.getKey())) {
                    if (media.getMetadata().get("title").toString()!= null)
                        setTitle(media.getMetadata().get("title").toString());

                }
                else if ("artist".equals(change.getKey())){
                    setArtist(media.getMetadata().get("artist").toString());
                }
                else if ("album".equals(change.getKey())){
                    setAlbum(media.getMetadata().get("album").toString());
                }
                else if ("genre".equals(change.getKey())){
                    setGenre(media.getMetadata().get("genre").toString());
                }
                /*else if (media.getDuration()!= null){
                    setDuration(media.getDuration().toSeconds());
                    if ( media.getMetadata().get("length")!= null)
                        setDuration((Double) media.getMetadata().get("length"));
                }*/
            }
        });

    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }

    public Double getDuration() {
        return duration;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }
}
