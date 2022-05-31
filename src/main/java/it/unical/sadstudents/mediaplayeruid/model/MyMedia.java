package it.unical.sadstudents.mediaplayeruid.model;

import javafx.collections.MapChangeListener;
import javafx.scene.media.Media;

import java.io.File;

public class MyMedia {
    private Media media;
    private String title = "";
    private String artist = "";
    private String album = "";
    private String genre = "";
    private File file;
    private Double duration = 0.0;
    private Integer releaseYear = 0;

    private boolean isReturnable = false;

    public MyMedia() { }

    public MyMedia(File file) {
        this.file = file;
        media = new Media(this.file.toURI().toString());
        duration = media.getDuration().toSeconds();
        
        media.getMetadata().addListener((MapChangeListener<String, Object>) change -> {
            if(change.wasAdded()){
                if("title".equals(change.getKey())) {
                    title = media.getMetadata().get("title").toString();
                    System.out.println("title: " + title);
                }
                else if ("artist".equals(change.getKey())){
                    artist = media.getMetadata().get("artist").toString();
                    System.out.println("artist: " + artist);
                }
                else if ("album".equals(change.getKey())){
                    album = media.getMetadata().get("album").toString();
                    System.out.println("album: " + album);
                }
                else if ("genre".equals(change.getKey())){
                    genre = media.getMetadata().get("genre").toString();
                    System.out.println("genre: " + genre);
                }
            }
        });

    }

    public void setMedia(Media media) {
        this.media = media;
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

    public void setFile(File file) {
        this.file = file;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public Media getMedia() {
        return media;
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

    public File getFile() {
        return file;
    }

    public Double getDuration() {
        return duration;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }
}
