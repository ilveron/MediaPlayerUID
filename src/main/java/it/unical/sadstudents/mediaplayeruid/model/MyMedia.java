package it.unical.sadstudents.mediaplayeruid.model;

import javafx.collections.MapChangeListener;
import javafx.scene.media.Media;

import java.io.File;
import java.io.IOException;
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
    private Double length;
    private Integer year;


    private boolean isReturnable = false;

    public MyMedia() { }

    public MyMedia(File file) {
        path = file.toURI().toString();
        setTitle(file.getName());
        Media media = new Media(path);

        media.getMetadata().addListener((MapChangeListener<String, Object>) change -> {
            if(change.wasAdded()) {
                if ("title".equals(change.getKey())) {
                    if (media.getMetadata().get("title").toString() != null)
                        setTitle(media.getMetadata().get("title").toString());
                }
                else if ("artist".equals(change.getKey())) {
                    setArtist(media.getMetadata().get("artist").toString());
                }
                else if ("album".equals(change.getKey())) {
                    setAlbum(media.getMetadata().get("album").toString());
                }
                else if ("genre".equals(change.getKey())) {
                    setGenre(media.getMetadata().get("genre").toString());
                }
                else if ("year".equals(change.getKey())) {
                    setYear(Integer.parseInt(media.getMetadata().get("year").toString()));
                }
                else if (media.getMetadata().get("length") != null)
                    setLength(Double.parseDouble(media.getMetadata().get("length").toString()));
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

    public void setLength(Double length) {
        this.length = length;
    }

    public void setYear(Integer year) {
        this.year = year;
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

    public Double getLength() {
        return length;
    }

    public Integer getYear() {
        return year;
    }
}
