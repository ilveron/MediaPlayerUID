package it.unical.sadstudents.mediaplayeruid.model;

public class Song {
    private String name;
    private String author;
    private String album;
    private Integer durata;
    private Integer playedTimes;
    private String path;
    private String pathImageAlbum;

    public Song(String name, String author, String album, Integer durata, Integer playedTimes, String path, String pathImageAlbum) {
        this.name = name;
        this.author = author;
        this.album = album;
        this.durata = durata;
        this.playedTimes = playedTimes;
        this.path = path;
        this.pathImageAlbum = pathImageAlbum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public Integer getDurata() {
        return durata;
    }

    public void setDurata(Integer durata) {
        this.durata = durata;
    }

    public Integer getPlayedTimes() {
        return playedTimes;
    }

    public void setPlayedTimes(Integer playedTimes) {
        this.playedTimes = playedTimes;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPathImageAlbum() {
        return pathImageAlbum;
    }

    public void setPathImageAlbum(String pathImageAlbum) {
        this.pathImageAlbum = pathImageAlbum;
    }
}
