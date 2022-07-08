package it.unical.sadstudents.mediaplayeruid.model;

import javafx.collections.MapChangeListener;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.lang.Object;
import java.util.Objects;


public class MyMedia  {
    //VARIABLES-DATA
    private String title = "";
    private String artist = "N/A";
    private String album = "N/A";
    private String genre = "N/A";
    private String path = "";
    private String length="00:00:00";
    private String year="N/A";
    private String imageUrl="";
    private String delete="fa-close";


    // TODO: 03/06/2022 funzioni per ordinamento nelle varie liste/library 
    // TODO:

    //CLASS CONSTRUCTOR

    public MyMedia(String title, String artist, String album, String genre, String path, String length, String year, String imageUrl) {
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.genre = genre;
        this.path = path;
        this.length = length;
        this.year = year;
        this.imageUrl = imageUrl;
    }

    public MyMedia() { }
    public MyMedia(File file) {
        path = file.toURI().toString();
        setTitle(file.getName());
   }
    //END CLASS CONSTRUCTOR

    //VARIABLES GETTERS AND SETTERS





    public void setTitle(String title) { this.title = title; }
    public void setArtist(String artist) { this.artist = artist; }
    public void setAlbum(String album) { this.album = album; }
    public void setLength(String length) {
        this.length = length;
    }
    public void setYear(String year) { this.year = year; }
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }
    public String getTitle() { return title; }
    public String getArtist() { return artist; }
    public String getAlbum() { return album; }
    public String getLength() { return length; }
    public String getYear() { return year; }

    public String getDelete() {
        return delete;
    }

    public void setDelete(String delete) {
        this.delete = delete;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageURL) {
        this.imageUrl = imageURL;
    }

    @Override
   public String toString() {
        return  title + '\'' + artist + '\'' +album + '\'' +genre + '\'' +path + '\'' +length +'\''+year ;
    }

    /*@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyMedia myMedia = (MyMedia) o;
        return Objects.equals(title, myMedia.title) && Objects.equals(artist, myMedia.artist) && Objects.equals(album, myMedia.album) && Objects.equals(genre, myMedia.genre) && Objects.equals(path, myMedia.path) && Objects.equals(length, myMedia.length) && Objects.equals(year, myMedia.year);
    }*/

    //END VARIABLES GETTERS AND SETTERS


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MyMedia)) return false;
        MyMedia myMedia = (MyMedia) o;
        return getPath().equals(myMedia.getPath());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPath());
    }

}
