package it.unical.sadstudents.mediaplayeruid.model;

import javafx.collections.MapChangeListener;
import javafx.scene.media.Media;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

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
        media = new Media(file.toURI().toString());
        setDuration(media.getDuration().toSeconds());


        media.getMetadata().addListener((MapChangeListener<String, Object>) change -> {
            if(change.wasAdded()){
                if("title".equals(change.getKey())) {
                    setTitle(title = media.getMetadata().get("title").toString());
                    System.out.println("title: " + title);
                }
                else if ("artist".equals(change.getKey())){
                    setArtist(media.getMetadata().get("artist").toString());
                    System.out.println("artist: " + artist);
                }
                else if ("album".equals(change.getKey())){
                    setAlbum(media.getMetadata().get("album").toString());
                    System.out.println("album: " + album);
                }
                else if ("genre".equals(change.getKey())){
                    setGenre(media.getMetadata().get("genre").toString());
                    System.out.println("genre: " + genre);
                }
                else if ("lenght".equals(change.getKey())){
                    setDuration((Double) media.getMetadata().get("lenght"));
                    System.out.println(duration);

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

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
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
